package org.pentales.pentalesrest.models.entities.rating.share.dto

import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.rating.share.*
import org.pentales.pentalesrest.models.entities.user.*

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