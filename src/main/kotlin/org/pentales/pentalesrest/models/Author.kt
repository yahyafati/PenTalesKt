package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @field:NotBlank
    var name: String = ""
) : IModel

