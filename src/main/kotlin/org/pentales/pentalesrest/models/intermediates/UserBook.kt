package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class UserBook(
    @EmbeddedId
    var userBook: UserBookKey = UserBookKey(),

    @Enumerated(EnumType.STRING)
    var status: UserBookReadStatus = UserBookReadStatus.NONE,
    var likeBook: Boolean = false,
) : IAudit() {}