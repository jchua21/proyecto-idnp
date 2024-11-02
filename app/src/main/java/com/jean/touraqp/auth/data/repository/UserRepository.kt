package com.jean.touraqp.auth.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.auth.data.remote.collection.DBCollection
import com.jean.touraqp.auth.data.utils.PasswordHasher
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.ResourceResult
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

    suspend fun logInUser(email: String, password: String) : Flow<ResourceResult<User>>  = flow {
        try {
            emit(ResourceResult.Loading())
            val user = usersCollection.whereEqualTo("email", email).get().await()

            if (user.isEmpty) {
                throw Exception("User not registered")
            }

            val userRegistered = user.documents[0]
            val isCorrectPassword = passwordHasher.verify(
                password = password,
                hashPassword = userRegistered.getString("password")
                    ?: throw Error("Password Field does not exist in DB")
            )

            if (!isCorrectPassword) {
                throw Exception("Invalid Email or Password")
            }

            emit(ResourceResult.Success(message = "Authenticated!!!"))
        } catch (e: Exception) {
            emit(ResourceResult.Error(message = e.message))
        }

    }

    suspend fun signUpUser(user: UserDto): Flow<ResourceResult<User>> = flow {
        try {
            emit(ResourceResult.Loading())
            // Check if the email is not repeated
            val userWithEmail = usersCollection.whereEqualTo("email", user.email).get().await()
            if (!userWithEmail.isEmpty) {
                throw Exception("Email already in use")
            }

            //Hash Password
            val hashedPassword = passwordHasher.hash(user.password)
            val userWithPasswordHashed = user.copy(password = hashedPassword)

            Log.d(TAG, "signUpUser: HELLO?")

            // Sign up the user
            val userReference = usersCollection.add(userWithPasswordHashed).await()

            //Send response
            val userAdded = userReference.get().await().toObject(User::class.java)
            emit(ResourceResult.Success( data = userAdded , message = "User Added successfully"))

        } catch (e: Exception) {
            Log.d(TAG, "Something went wrong : ${e.message}")
            emit(ResourceResult.Error(message = e.message))
        }
    }
}