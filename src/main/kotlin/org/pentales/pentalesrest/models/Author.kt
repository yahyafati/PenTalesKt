package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(
    indexes = [Index(name = "author_goodreads_author_id_idx", columnList = "goodReadsAuthorId", unique = true)]
)
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @field:NotBlank
    var name: String = "",
    @Column(nullable = true)
    var goodReadsAuthorId: Long = 0L,
) : IModel() {

    override fun toString(): String {
        return "Author(id=$id, name='$name', goodReadsAuthorId=$goodReadsAuthorId)"
    }
}

