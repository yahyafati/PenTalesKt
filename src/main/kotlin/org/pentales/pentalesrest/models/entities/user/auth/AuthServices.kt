package org.pentales.pentalesrest.models.entities.user.auth

import jakarta.validation.*
import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.auth.dto.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.models.entities.user.verification.*
import org.pentales.pentalesrest.models.misc.email.*
import org.pentales.pentalesrest.utils.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import org.springframework.validation.annotation.*

@Service
class AuthServices(
    private val userServices: IUserServices,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationFacade: IAuthenticationFacade,
    private val verificationCodeService: IVerificationCodeServices,
    private val emailService: IEmailService,
) : IAuthServices {

    override fun register(
        @Validated
        registerUser: RegisterUser
    ): User {
        val user = registerUser.toUser()
        if (userServices.existsByUsername(user.username)) {
            throw ValidationException("Username already exists")
        }
        if (userServices.existsByEmail(user.email)) {
            throw ValidationException("Email already exists")
        }
        user.password = passwordEncoder.encode(user.password)
        user.isEnabled = false

        val profile = user.profile
        if (profile != null && profile.profilePicture.isNullOrBlank()) {
            profile.profilePicture = UserUtils.getProfileAvatar(profile.firstName, profile.lastName)
        }
        val savedUser = userServices.save(user)
        val verificationCode = verificationCodeService.createVerificationCode(savedUser.id)
        verificationCodeService.sendVerificationCode(savedUser.email, verificationCode)
        return savedUser
    }

    override fun getCurrentUser(): User {
        return authenticationFacade.forcedCurrentUser
    }

    override fun isUsernameAvailable(username: String): Boolean {
        return !userServices.existsByUsername(username)
    }

    @Transactional
    override fun verifyEmail(verifyUser: VerifyUserDto): IAuthServices.VerificationStatus {
        val verificationCode = verificationCodeService.validateVerificationCode(code = verifyUser.code)
            ?: return IAuthServices.VerificationStatus(false, null)

        val user = userServices.findById(verificationCode.userId)
        user.isEnabled = true
        user.isVerified = true
        val savedUser = userServices.save(user)
        verificationCodeService.deleteVerificationCodeByCode(verifyUser.code)
        return IAuthServices.VerificationStatus(true, savedUser)
    }

    override fun resendVerificationEmail() {
        val user = authenticationFacade.forcedCurrentUser
        if (user.isVerified) {
            throw ValidationException("User already verified")
        }
        val verificationCode = verificationCodeService.createVerificationCode(user.id)
        verificationCodeService.sendVerificationCode(user.email, verificationCode)
    }

    override fun forgotPassword(forgotPasswordDto: ForgotPasswordDto) {
        val email = forgotPasswordDto.email
        val user = userServices.findByEmail(email) ?: throw ValidationException("User not found")
        val verificationCode = verificationCodeService.createVerificationCode(user.id)
        verificationCodeService.sendForgotPasswordCode(
            user.email,
            verificationCode,
            callbackURL = forgotPasswordDto.callbackURL
        )
    }

    @Transactional
    override fun resetPassword(resetUser: ResetUserDto) {
        val verificationCode = verificationCodeService.validateVerificationCode(code = resetUser.code)
            ?: throw ValidationException("Invalid verification code")

        val user = userServices.findById(verificationCode.userId)
        val newPassword = UserUtils.generateRandomPassword()
        user.password = passwordEncoder.encode(newPassword)
        userServices.save(user)
        verificationCodeService.deleteVerificationCodeByCode(resetUser.code)
        sendNewPassword(user.email, newPassword)
    }

    override fun sendNewPassword(email: String, newPassword: String) {
        val emailHTMLTemplate = EmailTemplateUtils.createEmailTemplate(
            content = """
                <h1>Password Reset</h1>
                
                <p>Your new password is: <b>$newPassword</b></p>
                
                <p>For security reasons, please change your password after logging in.</p>
                
                <p>
                    <b>If you did not request a password reset, please contact us immediately. Reply to this email as soon as possible!</b>
                </p>
                
                <p>Please do not share this email with anyone.</p>
                
                <p>Please delete this email after you have memorized your new password.</p>
                
                <p>Thank you.</p>
            """.trimIndent(),
            "Your new password is: $newPassword"
        )
        emailService.sendEmail(
            email,
            "ReadingRealm - Password Reset Successfully",
            emailHTMLTemplate,
            true
        )
    }
}