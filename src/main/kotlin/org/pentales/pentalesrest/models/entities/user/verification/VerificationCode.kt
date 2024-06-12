package org.pentales.pentalesrest.models.entities.user.verification

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.interfaces.*

@Entity
@Table(name = "verification_code")
class VerificationCode(
    var userId: Long = 0,
    var code: String = "",
    var requestAddress: String = ""
) : IModel()