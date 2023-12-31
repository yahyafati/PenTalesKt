package org.pentales.pentalesrest.dto.activity

import org.pentales.pentalesrest.models.*

data class ActivityShareDto(
    var quote: String? = "",
) {

    constructor(share: Share) : this(
        quote = share.shareQuote,
    )
}
