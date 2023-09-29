package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.intermediates.*

@Entity
class Book(

    @Id
    override var id: Long? = null,

    @field:NotBlank
    var title: String? = null,
    var description: String? = null,
    var ISBN: String? = null,
    var publicationYear: Int? = null,

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var authors: List<BookAuthor?>? = ArrayList(),

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var genres: List<BookGenre> = ArrayList(),

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var languages: List<BookLanguage> = ArrayList(),

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "book")
    var publishers: List<BookPublisher> = ArrayList()

) : IModel