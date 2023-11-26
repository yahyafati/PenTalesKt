package org.pentales.pentalesrest.models

import jakarta.persistence.*
import java.time.*

@Entity
class Goal(
    var title: String = "",
    var description: String = "",
    @field:Column(unique = true)
    var year: Int = Year.now().value,
) : IModel() {}