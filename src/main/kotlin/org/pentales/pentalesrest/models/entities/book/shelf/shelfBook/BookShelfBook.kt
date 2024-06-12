package org.pentales.pentalesrest.models.entities.book.shelf.shelfBook

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.activity.book.*
import org.pentales.pentalesrest.models.entities.book.shelf.*
import org.pentales.pentalesrest.models.entities.entityKeys.*

@Entity
class BookShelfBook(
    @EmbeddedId
    var id: BookShelfBookKey = BookShelfBookKey(),

    @MapsId("bookId")
    @ManyToOne
    var book: ActivityBook = ActivityBook(),

    @MapsId("bookShelfId")
    @ManyToOne
    var bookShelf: BookShelf = BookShelf(),

    var sortOrder: Int = 0,
)