package org.pentales.pentalesrest.models.entities.book.file

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.user.*
import java.sql.*

@Entity
class BookFile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @Column(columnDefinition = "TEXT")
    var path: String = "",
    @ManyToOne
    var owner: User = User(),
    @ManyToOne
    var book: org.pentales.pentalesrest.models.entities.book.Book = org.pentales.pentalesrest.models.entities.book.Book(),
    var isPublic: Boolean = true
) : IModel() {

    @Transient
    @JsonIgnore
    var __progress: String = ""

    @Transient
    @JsonIgnore
    var __lastRead: Timestamp? = null
}

