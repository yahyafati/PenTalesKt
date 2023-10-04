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
    var description: String = "",
    var ISBN: String = "",
    var publicationYear: Int = 0,
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var authors: List<BookAuthor> = ArrayList(),
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var genres: List<BookGenre> = ArrayList(),
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var languages: List<BookLanguage> = ArrayList(),
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var publishers: List<BookPublisher> = ArrayList()
) : IModel() {

    override fun toString(): String {
        return "Book(id=$id, title='$title', description='$description', ISBN='$ISBN', publicationYear=$publicationYear)"
    }
}