package org.pentales.pentalesrest.models.entities.rating.comment

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
class Comment(
    var comment: String = "",
    @ManyToOne
    var rating: Rating = Rating(),
    @ManyToOne
    var user: User = User(),
    var hidden: Boolean = false,
) : IModel()