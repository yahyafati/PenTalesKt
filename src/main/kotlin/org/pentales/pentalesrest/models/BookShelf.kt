package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.interfaces.*
import org.pentales.pentalesrest.models.intermediates.*

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