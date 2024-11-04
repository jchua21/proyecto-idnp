package com.jean.touraqp.auth.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.auth.data.remote.dto.toUserDomain
import com.jean.touraqp.auth.data.utils.PasswordHasher
import com.jean.touraqp.auth.domain.authentication.model.UserDomain
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.core.utils.ResourceResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val passwordHasher: PasswordHasher
) {
    private val usersCollection = db.collection(DBCollection.USER)

    companion object {
        const val TAG = "USER_REPOSITORY"
    }

    suspend fun logInUser(email: String, password: String): Flow<ResourceResult<UserDomain>> =
        flow {
            try {
                emit(ResourceResult.Loading())
                //Check if the user is registered
                val userWithEmail = usersCollection.whereEqualTo("email", email).get().await()
                val isRegistered = !userWithEmail.isEmpty
                if (!isRegistered) {
                    throw Exception("User not registered")
                }

                //Convert userDto
                val firstUserResult = userWithEmail.documents[0]
                Log.d(TAG, "logInUser: $firstUserResult")
                val currentUser =
                    firstUserResult.toObject<UserDto>()?.copy(id = firstUserResult.id)


                val isCorrectPassword = passwordHasher.verify(
                    password = password,
                    hashPassword = currentUser?.password
                        ?: throw Error("Password Field does not exist in DB")
                )

                if (!isCorrectPassword) {
                    throw Exception("Invalid Email or Password")
                }

                Log.d(TAG, "logInUser: LOGGG")
                emit(
                    ResourceResult.Success(
                        message = "Authenticated!!!",
                        data = currentUser.toUserDomain()
                    )
                )
            } catch (e: Exception) {
                Log.d(TAG, "Something went wrong : ${e.message}")
                emit(ResourceResult.Error(message = e.message))
            }

        }

    suspend fun signUpUser(user: UserDto): Flow<ResourceResult<UserDomain>> = flow {
        try {
            emit(ResourceResult.Loading())
            // Check if the email is not repeated
            val userWithEmail = usersCollection.whereEqualTo("email", user.email).get().await()
            val isEmailInUse = !userWithEmail.isEmpty
            if (isEmailInUse) {
                throw Exception("Email already in use")
            }

            //Hash Password
            val hashedPassword = passwordHasher.hash(user.password)
            val userWithPasswordHashed =
                user.copy(
                    password = hashedPassword,
                )

            // Sign up the user
            val userQueryResult = usersCollection.add(userWithPasswordHashed).await()
            //Send response
            val userAdded =
                userQueryResult.get().await().toObject<UserDto>()?.copy(id = userQueryResult.id)

            emit(
                ResourceResult.Success(
                    data = userAdded?.toUserDomain(),
                    message = "User Registered successfully"
                )
            )

        } catch (e: Exception) {
            Log.d(TAG, "Something went wrong : ${e.message}")
            emit(ResourceResult.Error(message = e.message))
        }
    }
}