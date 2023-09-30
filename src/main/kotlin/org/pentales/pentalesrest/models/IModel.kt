package org.pentales.pentalesrest.models

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*
import org.hibernate.annotations.*
import java.sql.*
import java.time.*

@MappedSuperclass
abstract class IModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    open var createdAt: Timestamp = Timestamp.from(Instant.now())

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    open var updatedAt: Timestamp = Timestamp.from(Instant.now())
}
