package org.pentales.pentalesrest.models.enums

enum class EActivityType {

    RATING,
    COMMENT,
    SHARE, ;

    companion object {

        fun fromString(value: String): EActivityType {
            return when (value.uppercase()) {
                "RATING" -> RATING
                "COMMENT" -> COMMENT
                "SHARE" -> SHARE
                else -> throw IllegalArgumentException()
            }
        }
    }
}