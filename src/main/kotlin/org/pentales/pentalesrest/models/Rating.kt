package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class Rating(
    @ManyToOne
    var user: User = User(),
    @ManyToOne
    var book: Book = Book(),

    @field:Min(1)
    @field:Max(5)
    var value: Int = 3,
    var review: String = "",

    @OneToMany(mappedBy = "rating", cascade = [CascadeType.REMOVE])
    var comments: List<Comment> = listOf(),
) : IModel()