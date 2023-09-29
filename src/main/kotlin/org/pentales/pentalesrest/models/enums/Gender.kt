package org.pentales.pentalesrest.models.enums

import java.util.*

enum class Gender(private val value: String) {

    MALE("MALE"),
    FEMALE("FEMALE");

    fun hasValue(value: String?): Boolean {
        return value?.trim { it <= ' ' }?.equals(this.value, ignoreCase = true) ?: false
    }

    companion object {

        fun fromValue(value: String?): Gender? {
            val first = Arrays.stream(values()).filter { gender: Gender -> gender.hasValue(value) }.findFirst()
            return first.orElse(null)
        }
    }
}
