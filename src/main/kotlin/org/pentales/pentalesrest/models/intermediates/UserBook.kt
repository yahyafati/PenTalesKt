package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

@Entity
class UserBook(
    @ManyToOne
    var user: User = User(),
    @ManyToOne
    var book: Book = Book(),

    @Enumerated(EnumType.STRING)
    var status: UserBookReadStatus = UserBookReadStatus.NONE,
) : IModel() {}