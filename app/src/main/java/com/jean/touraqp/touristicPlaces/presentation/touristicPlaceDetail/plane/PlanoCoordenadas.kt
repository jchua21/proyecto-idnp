package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.plane

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PlanoCoordenadas(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paintWall = Paint()
    private val paintBorde = Paint()
    private val paintSeleccion = Paint()
    private val paintWindow = Paint()
    private val paintText = Paint()

    private val calleGranadaRect = Rect(130, 600, 170, 800)
    private val claustroNoviciasRect = Rect(410, 40, 540, 110)
    private val patioSilencioRect = Rect(260, 40, 390, 110)
    private val claustroNaranjosRect = Rect(260, 140, 340, 220)
    private val claustroMayorRect = Rect(130, 140, 190, 350)
    private val pinacotecaRect = Rect(200, 110, 250, 350)
    private val coroBajoRect = Rect(50, 110, 120, 350)
    private val santaCatalinaRect = Rect(50, 350, 120, 800)
    private val nuevoMonasterioRect = Rect(340, 230, 550, 550)
    private val piletaRect = Rect()
    private val toledoRect = Rect(190, 600, 300, 800)
    private val sevillaRect = Rect(140, 810, 300, 860)
    private val cordovaRect = Rect(250, 350, 300, 500)

    interface OnAreaClickListener {
        fun onAreaClicked(areaName: String)
    }

    private var listener: OnAreaClickListener? = null

    init {
        // Configuración de pinceles
        paintWall.apply {
            color = Color.LTGRAY
            style = Paint.Style.FILL
            strokeWidth = 8f
        }

        paintBorde.apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        paintSeleccion.apply {
            color = Color.TRANSPARENT
            style = Paint.Style.FILL
            strokeWidth = 4f
        }

        paintWindow.apply {
            color = Color.CYAN
            style = Paint.Style.FILL
        }

        paintText.apply {
            color = Color.BLACK
            textSize = 15f
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height
        val scaleFactor = minOf(width / 600f, height / 1000f)

        canvas.scale(scaleFactor, scaleFactor)

        // Dibujo de bordes y áreas
        canvas.drawRect(50f, 20f, 550f, 1100f, paintBorde)

        // Áreas seleccionables
        canvas.drawRect(claustroNoviciasRect, paintSeleccion)
        canvas.drawRect(patioSilencioRect, paintSeleccion)
        canvas.drawRect(claustroNaranjosRect, paintSeleccion)
        canvas.drawRect(claustroMayorRect, paintSeleccion)
        canvas.drawRect(piletaRect, paintWall)
        canvas.drawRect(coroBajoRect, paintWall)
        canvas.drawRect(santaCatalinaRect, paintWall)
        canvas.drawRect(nuevoMonasterioRect, paintWall)
        canvas.drawCircle(138f, 565f, 15f, paintWindow)
        canvas.drawRect(calleGranadaRect, paintSeleccion)
        canvas.drawRect(toledoRect, paintSeleccion)
        canvas.drawRect(sevillaRect, paintSeleccion)
        canvas.drawRect(cordovaRect, paintSeleccion)


        //Resto de planos dibujados
        canvas.drawRect(50f, 20f, 550f, 55f, paintWall)
        canvas.drawRect(325f, 110f, 550f, 210f, paintWall)
        canvas.drawRect(50f, 110f, 300f, 140f, paintWall)
        canvas.drawRect(500f, 20f, 550f, 210f, paintWall)
        canvas.drawRect(380f, 20f, 430f, 100f, paintWall)
        canvas.drawRect(200f, 110f, 250f, 350f, paintWall)
        canvas.drawRect(270f, 230f, 550f, 550f, paintWall)
        canvas.drawRect(150f, 370f, 250f, 550f, paintWall)
        canvas.drawRect(150f, 580f, 250f, 800f, paintWall)
        canvas.drawRect(150f, 850f, 250f, 980f, paintWall)
        canvas.drawRect(270f, 850f, 400f, 1020f, paintWall)
        canvas.drawRect(270f, 500f, 400f, 800f, paintWall)

        // Dibujar textos
        canvas.drawText("Claustro", 435f, 80f, paintText)
        canvas.drawText("Novicias", 435f, 100f, paintText)

        canvas.drawText("Patio del", 300f, 80f, paintText)
        canvas.drawText("Silencio", 305f, 100f, paintText)

        canvas.drawText("Claustro", 130f, 250f, paintText)
        canvas.drawText("Mayor", 130f, 270f, paintText)

        canvas.drawText("Claustro", 260f, 170f, paintText)
        canvas.drawText("de los", 260f, 190f, paintText)
        canvas.drawText("Naranjos", 260f, 210f, paintText)

        canvas.drawText("Nuevo Monasterio", 360f, 380f, paintText)
        canvas.drawText("Calle sevilla", 160f, 830f, paintText)

        // Textos rotados
        canvas.save()
        canvas.rotate(90f, 75f, 130f)
        canvas.drawText("Coro Bajo", 75f, 130f, paintText)
        canvas.drawText("Iglesia Santa Catalina", 400f, 130f, paintText)
        canvas.drawText("Pinacoteca", 150f, -15f, paintText)
        canvas.drawText("Calle cordova", 300f, -50f, paintText)
        canvas.drawText("Calle Granada", 600f, 70f, paintText)
        canvas.drawText("Calle toledo", 650f, -50f, paintText)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val scaleFactor = minOf(width / 600f, height / 1000f)
            val x = event.x / scaleFactor
            val y = event.y / scaleFactor

            when {
                calleGranadaRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Calle Granada")
                claustroNoviciasRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Claustro Novicias")
                patioSilencioRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Patio del Silencio")
                claustroNaranjosRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Claustro de los Naranjos")
                claustroMayorRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Claustro Mayor")
                pinacotecaRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Pinacoteca")
                coroBajoRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Coro Bajo")
                santaCatalinaRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Iglesia Santa Catalina")
                nuevoMonasterioRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Nuevo Monasterio")
                toledoRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Calle Toledo")
                sevillaRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Calle Sevilla")
                cordovaRect.contains(x.toInt(), y.toInt()) -> listener?.onAreaClicked("Calle Cordova")
            }
        }
        return true
    }

    fun setOnAreaClickListener(listener: OnAreaClickListener) {
        this.listener = listener
    }
}
