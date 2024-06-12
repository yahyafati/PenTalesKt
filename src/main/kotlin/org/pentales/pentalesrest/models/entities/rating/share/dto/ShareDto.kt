package org.pentales.pentalesrest.models.entities.rating.share.dto

import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.rating.share.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.dto.*

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