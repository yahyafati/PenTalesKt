package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class Activity(
    @ManyToOne(cascade = [CascadeType.REMOVE])
    var rating: Rating? = null,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    var ratingComment: RatingComment? = null,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    var share: ActivityShare? = null,
) : IModel() {}