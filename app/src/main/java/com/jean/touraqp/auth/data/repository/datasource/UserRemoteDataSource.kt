package com.jean.touraqp.auth.data.repository.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.auth.data.remote.dto.toUser
import com.jean.touraqp.auth.data.utils.PasswordHasher
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.auth.domain.authentication.model.toUserDTO
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.core.utils.toObjectWithId
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val passwordHasher: PasswordHasher
) {
    companion object {
        const val TAG = "user_remote"
    }

    private val usersCollection = db.collection(DBCollection.USER)

    suspend fun registerUser(user: User): Result<User, Exception> {
        val registeredUser = try {
            val userDto = user.toUserDTO()
            // Check if the email is not repeated
            val userWithEmail = usersCollection.whereEqualTo("email", userDto.email).get().await()
            val isEmailInUse = !userWithEmail.isEmpty
            if (isEmailInUse) {
                throw Exception("Email already in use")
            }

            //Hash Password
            val hashedPassword = passwordHasher.hash(userDto.password)
            val userWithPasswordHashed =
                userDto.copy(
                    password = hashedPassword,
                )

            // Sign up the user
            val userQueryResult = usersCollection.add(userWithPasswordHashed).await()
            //Send response
            val userAdded =
                userQueryResult.get().await().toObjectWithId<UserDto>()

            userAdded.toUser()
        } catch (e: Exception) {
            Log.d(TAG, "Something went wrong : ${e.message}")
            return Result.Error(e);
        }

        return Result.Success(registeredUser)
    }

    suspend fun loginUser(email: String, password: String): Result<User, Exception> {
        val loggedUser = try {
            //Check if the user is registered
            val userWithEmail = usersCollection.whereEqualTo("email", email).get().await()
            val isRegistered = !userWithEmail.isEmpty
            if (!isRegistered) {
                throw Exception("User not registered")
            }

            //Convert userDto
            val firstUserResult = userWithEmail.documents[0]
            val currentUser =
                firstUserResult.toObjectWithId<UserDto>()

            val isCorrectPassword = passwordHasher.verify(
                password = password,
                hashPassword = currentUser.password
            )

            if (!isCorrectPassword) {
                throw Exception("Invalid Email or Password")
            }
            currentUser.toUser()
        } catch (e: Exception) {
            Log.d(TAG, "Something went wrong : ${e.message}")
            return Result.Error(e)
        }

        return Result.Success(loggedUser)
    }

    suspend fun getUserById(id: String): User{
        val result = usersCollection.document(id).get().await()
        val userDto = result.toObjectWithId<UserDto>()
        return userDto.toUser()
    }
}