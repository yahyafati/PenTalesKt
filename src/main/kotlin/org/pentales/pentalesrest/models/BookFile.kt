package org.pentales.pentalesrest.models

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*
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
    var book: Book = Book(),
    var isPublic: Boolean = true
) : IModel() {

    @Transient
    @JsonIgnore
    var __progress: String = ""

    @Transient
    @JsonIgnore
    var __lastRead: Timestamp? = null
}

