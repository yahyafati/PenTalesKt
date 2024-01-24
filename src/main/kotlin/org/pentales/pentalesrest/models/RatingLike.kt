package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class RatingLike(

    @EmbeddedId
    var id: UserRatingKey = UserRatingKey(),

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ratingId")
    var rating: Rating = Rating(),

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    var user: User = User()
)