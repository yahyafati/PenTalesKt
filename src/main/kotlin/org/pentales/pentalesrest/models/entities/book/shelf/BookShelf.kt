package org.pentales.pentalesrest.models.entities.book.shelf

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.book.shelf.shelfBook.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
class BookShelf(
    var title: String = "",
    var description: String = "",

    @Enumerated(EnumType.STRING)
    var access: EAccessLevel = EAccessLevel.PRIVATE,

    @OneToMany(mappedBy = "bookShelf", cascade = [CascadeType.ALL], orphanRemoval = true)
    var books: MutableList<BookShelfBook> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var owner: User = User(),
) : IModel() {

    var readLater: Boolean = false
}