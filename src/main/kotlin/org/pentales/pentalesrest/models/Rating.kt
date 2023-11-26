package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class Rating(
    @EmbeddedId
    var id: UserBookKey = UserBookKey(),

    @field:Min(1)
    @field:Max(5)
    var value: Int = 3,
    var comment: String = "",
) : IAudit()