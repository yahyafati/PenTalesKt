package org.pentales.pentalesrest.models.entities.user.profile

import java.util.*

enum class EGender(private val value: String) { MALE("MALE"),
    FEMALE("FEMALE");

    fun hasValue(value: String?): Boolean {
        return value?.trim { it <= ' ' }?.equals(this.value, ignoreCase = true) ?: false
    }

    companion object {

        fun fromValue(value: String?): EGender? {
            val first = Arrays.stream(values()).filter { gender: EGender -> gender.hasValue(value) }.findFirst()
            return first.orElse(null)
        }
    }
}
