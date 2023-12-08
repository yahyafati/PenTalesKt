package org.pentales.pentalesrest.models.enums

import java.util.*

enum class EAgeRestrictionType(
    val description: String
) {

    // Suitable for all ages
    G("General Audience"),

    // May contain material parents would need to explain to children
    PG("Parental Guidance"),

    // Suitable for viewers age 13 and older
    PG_13("PG-13"),

    // Restricted to viewers over 17 or 18, depending on region
    R("Restricted"),

    // Not suitable for viewers under 17 or 18
    NC_17("Adults Only");

    fun hasDescription(description: String?): Boolean {
        return description?.trim { it <= ' ' }?.equals(this.description, ignoreCase = true) ?: false
    }

    companion object {

        fun fromDescription(description: String?): EAgeRestrictionType? {
            val first = Arrays.stream(values()).filter { type: EAgeRestrictionType ->
                type.hasDescription(
                    description
                )
            }.findFirst()
            return first.orElse(null)
        }
    }
}
