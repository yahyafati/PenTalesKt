package org.pentales.pentalesrest.models.entities.book.author

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.entities.interfaces.*

@Entity
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @field:NotBlank
    var name: String = "",
    @Column(nullable = true)
    @JsonIgnore
    var goodReadsAuthorId: Long = 0L,
) : IModel() {

    override fun toString(): String {
        return "Author(id=$id, name='$name', goodReadsAuthorId=$goodReadsAuthorId)"
    }
}

