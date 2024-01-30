package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class Rating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,

    @ManyToOne
    var user: User = User(),
    @ManyToOne
    var book: Book = Book(),

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