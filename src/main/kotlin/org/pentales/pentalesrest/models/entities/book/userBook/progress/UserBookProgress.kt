package org.pentales.pentalesrest.models.entities.book.userBook.progress

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.book.file.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
@Table(name = "user_book_file_progress")
class UserBookProgress(
    @EmbeddedId
    val id: UserBookFileKey = UserBookFileKey(),

    @ManyToOne
    @MapsId("userId")
    val user: User = User(),
    @ManyToOne
    @MapsId("bookFileId")
    val bookFile: BookFile = BookFile(),
    var progress: String = ""
) : IAudit() {

    override fun toString(): String {
        return "UserBookProgress(id=$id, user=$user, book=$bookFile, progress='$progress')"
    }
}
