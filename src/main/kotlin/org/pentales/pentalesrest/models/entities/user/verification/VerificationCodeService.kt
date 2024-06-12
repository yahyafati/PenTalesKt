package org.pentales.pentalesrest.models.entities.user.verification

import org.pentales.pentalesrest.models.misc.email.*
import org.pentales.pentalesrest.utils.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*

@Service
class VerificationCodeService(
    private val verificationCodeRepository: VerificationCodeRepository,
    private val emailService: IEmailService,
) : IVerificationCodeServices {

    @Value("\${org.pen-tales.verification-code.length}")
    private val verificationCodeLength: Int = 6

    @Value("\${org.pen-tales.email.template.base}")
    private var templateURL: String = "classpath:templates/email"

    override fun createVerificationCode(userId: Long): VerificationCode {
        val verificationCode = VerificationCode(userId = userId)
        verificationCode.code = IVerificationCodeServices.generateVerificationCode(verificationCodeLength)
        verificationCode.requestAddress = ServletUtil.getIPAddressOfCurrentRequest()
        return verificationCodeRepository.save(verificationCode)
    }

    override fun findVerificationCodeByCode(code: String): VerificationCode? {
        return verificationCodeRepository.findByCode(code)
    }

    override fun findVerificationCodeByUserId(userId: Long): VerificationCode? {
        return verificationCodeRepository.findByUserId(userId)
    }

    override fun validateVerificationCode(code: String): VerificationCode? {
        val verificationCode = findVerificationCodeByCode(code) ?: return null

        val address = ServletUtil.getIPAddressOfCurrentRequest()
        return if (verificationCode.requestAddress == address) {
            verificationCode
        } else {
            null
        }
    }

    override fun deleteVerificationCodeByUserId(userId: Long) {
        verificationCodeRepository.deleteByUserId(userId)
    }

    override fun deleteVerificationCodeByCode(code: String) {
        verificationCodeRepository.deleteByCode(code)
    }

    override fun deleteById(id: Long) {
        verificationCodeRepository.deleteById(id)
    }

    override fun sendVerificationCode(email: String, verificationCode: VerificationCode) {
        val imageURL = "https://i.postimg.cc/3JsMm4x8/undraw-secure-login-pdn4.png"
        val emailHTMLTemplate = EmailTemplateUtils.createEmailTemplate(
            baseTemplate = EmailTemplateUtils.getBaseTemplate(templateURL),
            content = """
                <h1>Verification Code</h1>
                <p>Your verification code is: <strong>${verificationCode.code}</strong></p>
                
                <p>Thank you for using Reading Realm!</p>
            """.trimIndent(),
            imageURL = imageURL
        )


        emailService.sendEmail(
            email,
            "Verification Code",
            emailHTMLTemplate,
            true
        )
    }

    override fun sendForgotPasswordCode(email: String, verificationCode: VerificationCode, callbackURL: String?) {
        val imageURL = "https://i.postimg.cc/3JsMm4x8/undraw-secure-login-pdn4.png"
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        var resetPasswordURL = "$baseURL/api/auth/reset-password?code=${verificationCode.code}&email=$email"
        if (callbackURL != null) {
            resetPasswordURL += "&callback=$callbackURL"
        }
        val emailHTMLTemplate = EmailTemplateUtils.createEmailTemplate(
            baseTemplate = EmailTemplateUtils.getBaseTemplate(templateURL),
            content = """
                <h1>Forgot Password</h1>
                
                <p>You have requested to reset your password.</p>
                
                <p>Click the button below to reset your password:</p>
                
                <a href="$resetPasswordURL" style="display: block; padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; width: 200px; text-align: center; margin: 20px auto;">
                 Reset Password
                </a>
                
                <p>If the link does not work, copy and paste the following URL into your browser:</p>
                <p>
                    <a href="$resetPasswordURL" style="color: #4CAF50;">
                        $resetPasswordURL
                    </a>
                </p>
                
                <p>If you did not request this code, please ignore this email.</p>
                <p>Thank you for using Reading Realm!</p>
            """.trimIndent(),
            imageURL = imageURL
        )

        emailService.sendEmail(
            email,
            "Forgot Password Code",
            emailHTMLTemplate,
            true
        )
    }

}