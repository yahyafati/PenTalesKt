package org.pentales.pentalesrest.models.entities.activity.book

import jakarta.persistence.*
import jakarta.persistence.Id
import jakarta.persistence.Transient
import org.pentales.pentalesrest.models.entities.book.author.bookAuthor.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.springframework.data.annotation.*

@Entity
@Table(name = "book")
@Immutable
class ActivityBook(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    var title: String = "",
    @Column(columnDefinition = "TEXT")
    var coverImage: String = "",
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var authors: List<BookAuthor> = ArrayList(),
    @Transient
    var __averageRating: Double = 0.0,
    @Transient
    var __ratingCount: Long = 0L,
) : IModel() {

    constructor(book: org.pentales.pentalesrest.models.entities.book.Book) : this(
        id = book.id,
        title = book.title,
        coverImage = book.coverImage,
        authors = book.authors,
    )
}
