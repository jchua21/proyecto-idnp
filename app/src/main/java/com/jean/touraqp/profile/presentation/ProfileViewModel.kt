package com.jean.touraqp.profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.auth.domain.authentication.model.toUserUI
import com.jean.touraqp.auth.presentation.model.UserUI
import com.jean.touraqp.auth.presentation.model.toUser
import com.jean.touraqp.core.utils.onError
import com.jean.touraqp.core.utils.onSuccess
import com.jean.touraqp.profile.domain.usecases.UpdateProfileUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateProfileUser: UpdateProfileUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _effect = Channel<ProfileEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(e: ProfileEvent) {
        when (e) {
            is ProfileEvent.OnNameChanged -> {
                _state.update {
                    it.copy(
                        name = e.name,
                    )
                }
            }

            is ProfileEvent.OnEmailChanged -> {
                _state.update {
                    it.copy(
                        email = e.email,
                    )
                }

            }

            is ProfileEvent.OnUsernameChanged -> {
                _state.update {
                    it.copy(
                        username = e.username,
                    )
                }
            }

            is ProfileEvent.OnUpdateProfile -> {
                submit(e.userId)
            }

            is ProfileEvent.OnSyncProfile -> {
                onSyncProfile(e.email, e.username, e.name)
            }
        }
    }

    private fun onSyncProfile(email: String, username: String, name: String) {
        _state.update {
            it.copy(
                originalEmail = email,
                email = email,
                originalUsername = username,
                username = username,
                originalName = name,
                name = name
            )
        }
    }

    private fun submit(userId: String) {
        updateProfile(userId)
    }

    private fun updateProfile(userId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val updatedInfo = UserUI(
                id = userId,
                username = state.value.username,
                name = state.value.name,
                email = state.value.email,
            )
            updateProfileUser.execute(updatedInfo.toUser())
                .onSuccess {
                    _effect.send(ProfileEffect.OnSuccessProfileUpdated(it.toUserUI()))
                }
                .onError {
                    _effect.send(
                        ProfileEffect.OnErrorProfileUpdated(
                            it.message ?: "Something went wrong"
                        )
                    )
                }

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

}