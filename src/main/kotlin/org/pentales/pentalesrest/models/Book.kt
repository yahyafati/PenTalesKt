package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.intermediates.*

@Entity
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @field:NotBlank
    var title: String = "",
    @Column(columnDefinition = "TEXT")
    var description: String = "",
    @Column(nullable = true)
    var goodreadsId: Long = 0L,
    var ISBN: String = "",
    var ISBN13: String = "",
    var goodreadsLink: String = "",
    var languageCode: String = "",
    var coverImage: String = "",
    var publicationYear: Int = 0,
    var publisher: String = "",
    var pageCount: Int = 0,
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var authors: List<BookAuthor> = ArrayList(),
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var genres: List<BookGenre> = ArrayList(),
) : IModel() {

    override fun toString(): String {
        return "Book(id=$id, title='$title', description='$description', ISBN='$ISBN', publicationYear=$publicationYear)"
    }
}