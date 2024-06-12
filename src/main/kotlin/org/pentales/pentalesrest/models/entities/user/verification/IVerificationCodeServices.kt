package org.pentales.pentalesrest.models.entities.user.verification

interface IVerificationCodeServices {

    companion object {

        fun generateVerificationCode(length: Int): String {
            val chars = "0123456789"
            val code = StringBuilder()
            for (i in 0 until length) {
                code.append(chars.random())
            }
            return code.toString()
        }
    }

    fun createVerificationCode(userId: Long): VerificationCode

    fun findVerificationCodeByCode(code: String): VerificationCode?

    fun findVerificationCodeByUserId(userId: Long): VerificationCode?

    fun validateVerificationCode(code: String): VerificationCode?

    fun deleteVerificationCodeByUserId(userId: Long)

    fun deleteVerificationCodeByCode(code: String)
    fun deleteById(id: Long)

    fun sendVerificationCode(email: String, verificationCode: VerificationCode)
    fun sendForgotPasswordCode(email: String, verificationCode: VerificationCode, callbackURL: String? = null)
}