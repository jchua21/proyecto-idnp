package com.jean.touraqp.profile.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.jean.touraqp.R
import com.jean.touraqp.core.auth.AuthEvent
import com.jean.touraqp.core.auth.AuthViewModel
import com.jean.touraqp.databinding.FragmentProfileScreenBinding
import com.jean.touraqp.touristicPlaces.presentation.shared.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileScreenFragment : Fragment(R.layout.fragment_profile_screen) {

    private var binding: FragmentProfileScreenBinding? = null
    private val authViewModel: AuthViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    companion object {
        const val TAG = "profile_screen"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileScreenBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        syncUserInformation()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    profileViewModel.effect.collect() {
                        when (it) {
                            is ProfileEffect.OnErrorProfileUpdated -> {
                                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is ProfileEffect.OnSuccessProfileUpdated -> {
                                Toast.makeText(activity, "Information Updated", Toast.LENGTH_SHORT)
                                    .show()
                                authViewModel.onEvent(AuthEvent.OnInformationUserUpdated(it.userUpdated))
                                syncUserInformation()
                            }
                        }
                    }
                }

                launch {
                    profileViewModel.state.collect() { state ->

                        binding?.apply {
                            btnUpdateProfile.isEnabled =  state.isProfileModified && !state.isLoading
                        }
                    }
                }
            }
        }
    }

    private fun syncUserInformation() {
        //Sync user info
        binding?.apply {
            authViewModel.state.value.apply {
                profileUserImage.load(imageUrl) {
                    transformations(CircleCropTransformation())
                    crossfade(true)
                }
                profileUsername.setText(username)
                profileName.setText(name)
                profileEmail.setText(email)
                profilePassword.isEnabled = false
                profilePassword.setText("*".repeat(8))

                profileViewModel.onEvent(ProfileEvent.OnSyncProfile(
                    email = email,
                    username= username,
                    name = name,
                ))
            }
        }
    }

    private fun initListeners() {
        binding?.apply {
            profileUsername.doOnTextChanged { text, _, _, _ ->
                profileViewModel.onEvent(ProfileEvent.OnUsernameChanged(text.toString()))
            }
            profileName.doOnTextChanged { text, _, _, _ ->
                profileViewModel.onEvent(ProfileEvent.OnNameChanged(text.toString()))
            }
            profileEmail.doOnTextChanged { text, _, _, _ ->
                profileViewModel.onEvent(ProfileEvent.OnEmailChanged(text.toString()))
            }
            btnUpdateProfile.setOnClickListener() {
                profileViewModel.onEvent(ProfileEvent.OnUpdateProfile(authViewModel.state.value.id!!))
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}