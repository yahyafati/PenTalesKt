package org.pentales.pentalesrest.models

import jakarta.persistence.*

@Entity
class RatingComment(
    var comment: String = "",
    @ManyToOne
    var rating: Rating = Rating(),
) : IModel() {}