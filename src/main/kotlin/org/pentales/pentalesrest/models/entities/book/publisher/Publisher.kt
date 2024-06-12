package org.pentales.pentalesrest.models.entities.book.publisher

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.entities.interfaces.*

@Entity
class Publisher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @field:NotBlank
    var name: String = ""
) : IModel() {

    override fun toString(): String {
        return "Publisher(id=$id, name='$name')"
    }
}

