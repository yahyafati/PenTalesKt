package org.pentales.pentalesrest.services

import com.google.firebase.messaging.*
import org.pentales.pentalesrest.models.*

interface IPushNotificationService {

    fun sendPushNotificationToAllUsers(
        notification: Notification
    )

    fun sendPushNotificationToUser(
        notification: Notification,
        userId: Long
    )

    fun sendPushNotificationToUsers(
        notification: Notification,
        userIds: List<Long>
    )

    fun sendPushNotificationToToken(
        notification: Notification,
        token: String
    )

    fun registerDevice(
        token: String,
        user: User
    )

    fun unregisterDevice(
        token: String
    )

}