package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.interfaces.*
import org.pentales.pentalesrest.models.keys.*

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
) : IAudit() {}