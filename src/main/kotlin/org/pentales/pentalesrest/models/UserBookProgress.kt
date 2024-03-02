package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*
import org.pentales.pentalesrest.models.keys.*

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
