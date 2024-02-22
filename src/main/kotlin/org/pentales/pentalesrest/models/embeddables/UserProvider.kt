package org.pentales.pentalesrest.models.embeddables

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*

@Embeddable
class UserProvider(
    @Enumerated(EnumType.STRING)
    var provider: EAuthProvider? = EAuthProvider.LOCAL,

    var providerId: String = ""
) {

    override fun toString(): String {
        return "UserProvider(provider=$provider, providerId='$providerId')"
    }
}
