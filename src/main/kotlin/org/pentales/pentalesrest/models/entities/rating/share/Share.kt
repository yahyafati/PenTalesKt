package org.pentales.pentalesrest.models.entities.rating.share

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
class Share(
    @Column(columnDefinition = "TEXT", nullable = true)
    var shareQuote: String? = "",
    @ManyToOne
    var rating: Rating = Rating(),
    @ManyToOne
    var user: User = User(),
) : IModel()