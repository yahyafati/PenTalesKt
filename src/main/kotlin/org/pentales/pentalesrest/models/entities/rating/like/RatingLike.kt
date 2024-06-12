package org.pentales.pentalesrest.models.entities.rating.like

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*

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