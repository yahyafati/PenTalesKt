package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
class Genre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,

    @field:NotBlank
    var name: String? = null
) : IModel

