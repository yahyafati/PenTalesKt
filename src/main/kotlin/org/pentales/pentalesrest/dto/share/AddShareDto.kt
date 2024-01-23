package org.pentales.pentalesrest.dto.share

import org.pentales.pentalesrest.models.*

class AddShareDto(
    var quote: String? = "",
) {

    constructor(share: Share) : this(
        quote = share.shareQuote,
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