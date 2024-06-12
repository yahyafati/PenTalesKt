package org.pentales.pentalesrest.models.entities.book.userBook.status

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.book.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
class UserBookStatus(
    @EmbeddedId
    var id: UserBookKey = UserBookKey(),

    @ManyToOne
    @MapsId("userId")
    var user: User = User(),

    @ManyToOne
    @MapsId("bookId")
    var book: Book = Book(),

    @Enumerated(EnumType.STRING)
    var status: EUserBookReadStatus = EUserBookReadStatus.NONE,
) : IAudit()