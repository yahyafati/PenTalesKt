package org.pentales.pentalesrest.models.view

import jakarta.persistence.*
import jakarta.persistence.Id
import org.pentales.pentalesrest.models.enums.*
import org.springframework.data.annotation.*
import java.sql.*

@Entity
@Table(name = "activity")
@Immutable
data class Activity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: EActivityType = EActivityType.RATING,

    @Column(name = "updated_at")
    var updatedAt: Timestamp = Timestamp(System.currentTimeMillis()),
)