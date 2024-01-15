package org.pentales.pentalesrest.dto

class BasicResponseDto<T> (
    val data: T,
    val message: String,
    val status: Int
) {
    companion object {
        fun <T> ok(data: T, message: String = "OK"): BasicResponseDto<T> {
            return BasicResponseDto(data, message, 200)
        }

        fun <T> error(data: T, message: String = "Internal Error"): BasicResponseDto<T> {
            return BasicResponseDto(data, message, 500)
        }
    }
}