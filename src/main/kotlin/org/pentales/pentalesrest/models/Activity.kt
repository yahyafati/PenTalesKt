package org.pentales.pentalesrest.models

import jakarta.persistence.*

@Entity
class Activity(
    @ManyToOne
    var rating: Rating = Rating(),
    @ManyToOne
    var ratingComment: RatingComment = RatingComment(),
    @ManyToOne
    var follower: Follower = Follower(),
    @ManyToOne
    var share: ActivityShare? = null,
) : IModel() {}