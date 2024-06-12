package org.pentales.pentalesrest.models.entities.user

import java.util.*

enum class EAuthProvider {

    GOOGLE,
    FACEBOOK,
    GITHUB,
    LOCAL;

    companion object {

        fun from(name: String): EAuthProvider {
            return valueOf(name.uppercase(Locale.getDefault()))
        }
    }
}