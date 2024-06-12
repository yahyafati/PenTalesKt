package org.pentales.pentalesrest.models.entities.rating

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.rating.comment.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
class Rating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,

    @ManyToOne
    var user: User = User(),
    @ManyToOne
    var book: org.pentales.pentalesrest.models.entities.book.Book = org.pentales.pentalesrest.models.entities.book.Book(),

    @field:Min(1)
    @field:Max(5)
    var value: Int = 3,
    var review: String = "",
    var hidden: Boolean = false,

    @OneToMany(mappedBy = "rating", cascade = [CascadeType.REMOVE])
    var comments: List<Comment> = listOf(),
) : IModel() {

    @Transient
    var __likes: Long = 0

    @Transient
    var __isLiked: Boolean = false
}