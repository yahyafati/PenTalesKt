package org.pentales.pentalesrest.models.entities.book.genre

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.entities.interfaces.*

@Entity
class Genre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @field:NotBlank
    var name: String = ""
) : IModel() {

    override fun toString(): String {
        return "Genre(id=$id, name='$name')"
    }
}
