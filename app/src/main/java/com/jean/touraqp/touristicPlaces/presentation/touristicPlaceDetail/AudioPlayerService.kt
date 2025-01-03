package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.jean.touraqp.R

class AudioPlayerService : Service() {
    private val CHANNEL_ID = "AudioPlayerServiceChannel"
    private val NOTIFICATION_ID = 1
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var isAppInBackground = false

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaPlayer = MediaPlayer.create(this, R.raw.audio_sample)
        mediaPlayer?.isLooping = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        val placeName = intent?.getStringExtra("PLACE_NAME")
        // Llama a startForeground inmediatamente después de iniciar el servicio
        startForeground(NOTIFICATION_ID, createNotification())

        when (action) {
            "START" -> startPlayback(placeName)
            "PAUSE" -> pausePlayback()
            "RESUME" -> resumePlayback()
            "SHOW_NOTIFICATION" -> {
                isAppInBackground = true
                showNotification()
            }
            "HIDE_NOTIFICATION" -> {
                isAppInBackground = false
                hideNotification()
            }
            "STOP" -> stopPlayback() // Detiene el audio pero mantiene el servicio activo
            "TERMINATE" -> {
                stopPlayback()
                stopSelf() // Finaliza el servicio de manera explícita
            }
        }
        return START_STICKY
    }

    private fun createNotification(): Notification {
        val pauseIntent = Intent(this, AudioPlayerService::class.java).apply { action = "PAUSE" }
        val pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val resumeIntent = Intent(this, AudioPlayerService::class.java).apply { action = "RESUME" }
        val resumePendingIntent = PendingIntent.getService(this, 0, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this, AudioPlayerService::class.java).apply { action = "STOP" }
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("TourApp")
            .setContentText("Reproduciendo audio")
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .addAction(R.drawable.ic_pause, "Pausar", pausePendingIntent)
            .addAction(R.drawable.ic_sound, "Continuar", resumePendingIntent)
            .addAction(R.drawable.ic_stop, "Detener", stopPendingIntent)
            .build()
    }



    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Audio Player Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun showNotification() {
        if (mediaPlayer?.isPlaying == true || isPlaying) {
            val pauseIntent = Intent(this, AudioPlayerService::class.java).apply { action = "PAUSE" }
            val pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val resumeIntent = Intent(this, AudioPlayerService::class.java).apply { action = "RESUME" }
            val resumePendingIntent = PendingIntent.getService(this, 0, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val stopIntent = Intent(this, AudioPlayerService::class.java).apply { action = "STOP" }
            val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("TourApp")
                .setContentText("Reproduciendo audio")
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .addAction(R.drawable.ic_pause, "Pausar", pausePendingIntent)
                .addAction(R.drawable.ic_sound, "Continuar", resumePendingIntent)
                .addAction(R.drawable.ic_stop, "Detener", stopPendingIntent)
                .build()

            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun hideNotification() {
        stopForeground(true)
        val manager = getSystemService(NotificationManager::class.java)
        manager?.cancel(NOTIFICATION_ID)
    }

    private fun startPlayback(placeName: String?) {
        if (placeName == null) {
            Toast.makeText(this, "Nombre del lugar no especificado", Toast.LENGTH_SHORT).show()
            return
        }

        val audioResId = getAudioResourceId(placeName)
        if (audioResId == null) {
            Toast.makeText(this, "Audio no encontrado para $placeName", Toast.LENGTH_SHORT).show()
            return
        }

        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()  // Detén el audio actual si está sonando
                it.prepareAsync()
            }
            it.reset()  // Reinicia el MediaPlayer antes de asignar el nuevo recurso
            it.setDataSource(applicationContext, Uri.parse("android.resource://$packageName/raw/$audioResId"))
            it.prepare()
            it.start()
            isPlaying = true
            Toast.makeText(this, "Reproduciendo audio para $placeName", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAudioResourceId(placeName: String): Int? {
        return when (placeName) {
            "Monasterio de Santa Catalina" -> R.raw.audio_sample
            "Mirador de Yanahuara" -> R.raw.miradoryana
            "Las Caleras de Yura" -> R.raw.caleras
            "Museo de Arequipa" -> R.raw.museoaqp
            "Claustros de la Compañia" -> R.raw.cclaustro
            "El Cañon del Colca" -> R.raw.canion
            "Plaza de Armas de Arequipa" -> R.raw.plazaarm
            "Museo Santuarios Andinos" -> R.raw.andinos
            "Casa del Moral" -> R.raw.casamoral
            "Barrio de San Lázaro" -> R.raw.barriolaz
            "Molino de Sabandía" -> R.raw.molino
            else -> null
        }
    }


    private fun pausePlayback() {
        mediaPlayer?.takeIf { it.isPlaying }?.apply {
            pause()
            this@AudioPlayerService.isPlaying = true
            Toast.makeText(this@AudioPlayerService, "Audio pausado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resumePlayback() {
        mediaPlayer?.takeIf { !it.isPlaying }?.apply {
            start()
            this@AudioPlayerService.isPlaying = true
            Toast.makeText(this@AudioPlayerService, "Audio continuado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopPlayback() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause() // Pausa el audio
                seekTo(0) // Reinicia el audio al principio
            }
        }
        isPlaying = false
        Toast.makeText(this@AudioPlayerService, "Audio detenido", Toast.LENGTH_SHORT).show()
        hideNotification()
    }

}