package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

data class ShareDto(
    val id: Long? = 0,
    val quote: String? = "",
    var user: UserDto = UserDto(),
) {

    constructor(share: Share) : this(
        id = share.id,
        quote = share.shareQuote,
        user = UserDto(share.user),
    )

    fun toActivityShare(
        rating: Rating,
        user: User,
    ): Share {

        return Share(
            shareQuote = quote,
            rating = rating,
            user = user,
        )
    }
}