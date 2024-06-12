package org.pentales.pentalesrest.models.entities.user.verification

import org.springframework.data.jpa.repository.*

interface VerificationCodeRepository : JpaRepository<VerificationCode, Long> {

    fun findByCode(code: String): VerificationCode?
    fun findByUserId(userId: Long): VerificationCode?

    fun deleteByUserId(userId: Long)
    fun deleteByCode(code: String)
}