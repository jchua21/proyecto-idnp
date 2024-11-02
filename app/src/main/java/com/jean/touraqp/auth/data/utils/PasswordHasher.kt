package com.jean.touraqp.auth.data.utils

import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordHasher @Inject constructor(
    private var argon2Kt: Argon2Kt
) {

    fun hash(password: String): String{
        val hashResult: Argon2KtResult = argon2Kt.hash(
            mode = Argon2Mode.ARGON2_I,
            password = password.toByteArray(),
            salt = ByteArray(16),
            tCostInIterations = 5,
            mCostInKibibyte = 65536
        )

        return hashResult.encodedOutputAsString()
    }

    fun verify(password: String, hashPassword: String): Boolean{
        return argon2Kt.verify(
            mode = Argon2Mode.ARGON2_I,
            encoded = hashPassword,
            password = password.toByteArray(),
        )
    }
}