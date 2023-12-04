package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class Activity(
    @ManyToOne
    var rating: Rating? = Rating(),
    @ManyToOne
    var ratingComment: RatingComment? = RatingComment(),
    @ManyToOne
    var share: ActivityShare? = null,
) : IModel() {}