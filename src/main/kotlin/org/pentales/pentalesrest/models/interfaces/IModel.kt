package org.pentales.pentalesrest.models.interfaces

import jakarta.persistence.*
import java.io.*

@MappedSuperclass
abstract class IModel : Serializable, IAudit() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

}
