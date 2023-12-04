package org.pentales.pentalesrest.models

import jakarta.persistence.*
import java.time.*

@Entity
@Table(
    indexes = [Index(name = "goal_year_idx", columnList = "year", unique = true)]
)
class Goal(
    var title: String = "",
    var description: String = "",
    @field:Column(unique = true)
    var year: Int = Year.now().value,
) : IModel() {}