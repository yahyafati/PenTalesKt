package org.pentales.pentalesrest.models.entities.user.auth

import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.auth.dto.*
import org.pentales.pentalesrest.models.entities.user.dto.*

interface IAuthServices {

    data class VerificationStatus(
        val status: Boolean,
        val user: User?
    )

    fun register(registerUser: RegisterUser): User

    fun getCurrentUser(): User

    fun isUsernameAvailable(username: String): Boolean
    fun verifyEmail(verifyUser: VerifyUserDto): VerificationStatus
    fun resendVerificationEmail()
    fun forgotPassword(forgotPasswordDto: ForgotPasswordDto)
    fun resetPassword(resetUser: ResetUserDto)
    fun sendNewPassword(email: String, newPassword: String)
}