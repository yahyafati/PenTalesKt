package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class Comment(
    var comment: String = "",
    @ManyToOne
    var rating: Rating = Rating(),
    @ManyToOne
    var user: User = User(),
) : IModel()