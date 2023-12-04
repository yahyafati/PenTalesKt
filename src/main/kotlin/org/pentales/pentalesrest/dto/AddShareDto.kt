package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

class AddShareDto(
    var quote: String? = "",
) {

    constructor(activityShare: ActivityShare) : this(
        quote = activityShare.shareQuote,
    )

    fun toActivityShare(
        rating: Rating,
        user: User,
    ): ActivityShare {

        return ActivityShare(
            shareQuote = quote,
            rating = rating,
            user = user,
        )
    }
}