package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*

@Entity
class ReadList(
    var title: String = "",
    var description: String = "",

    @Enumerated(EnumType.STRING)
    var access: AccessLevel = AccessLevel.PRIVATE,

    @ManyToMany
    @JoinTable(
        name = "read_list_books",
        joinColumns = [JoinColumn(name = "read_list_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    var books: MutableList<Book> = mutableListOf(),
) : IModel() {}