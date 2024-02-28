package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*

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
    var book: Book = Book()
) : IModel()
