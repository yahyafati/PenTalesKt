package org.pentales.pentalesrest.models.entities.user

import jakarta.persistence.*

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
