package org.pentales.pentalesrest.dto.share

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

data class ShareDto(
    val id: Long? = 0,
    val quote: String? = "",
    var user: UserDto = UserDto(),
) {

    constructor(share: Share, baseURL: String) : this(
        id = share.id,
        quote = share.shareQuote,
        user = UserDto(share.user, baseURL),
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