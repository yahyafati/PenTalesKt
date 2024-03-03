package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class PushNotificationToken(
    @Id
    override var id: Long,
    val token: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User
) : IModel() {

    override fun toString(): String {
        return "PushNotificationToken(id=$id, token='$token', user=$user)"
    }
}