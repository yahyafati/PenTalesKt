package org.pentales.pentalesrest.models.entities.activity

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

        fun fromString(value: String, default: EActivityType): EActivityType {
            return try {
                fromString(value)
            } catch (e: Exception) {
                default
            }
        }
    }
}