package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class ActivityShare(
    @Column(columnDefinition = "TEXT", nullable = true)
    var shareQuote: String? = "",
    @ManyToOne
    var rating: Rating = Rating(),
    @ManyToOne
    var user: User = User(),
) : IModel() {}