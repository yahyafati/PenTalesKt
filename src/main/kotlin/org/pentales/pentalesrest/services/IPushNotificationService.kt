package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.models.*

interface IPushNotificationService {

    enum class ActionType {
        OPEN_USER_PROFILE,
        OPEN_REVIEW,
        OPEN_REVIEW_COMMENT,
    }

    fun sendPushNotificationToAllUsers(
        action: ActionType,
        data: Map<String, String> = emptyMap()
    )

    fun sendPushNotificationToUser(
        action: ActionType,
        userId: Long,
        data: Map<String, String> = emptyMap()
    )

    fun sendPushNotificationToUsers(
        action: ActionType,
        userIds: List<Long>,
        data: Map<String, String> = emptyMap()
    )

    fun sendPushNotificationToToken(
        action: ActionType,
        token: String,
        data: Map<String, String> = emptyMap()
    )

    fun registerDevice(
        token: String,
        user: User,
        data: Map<String, String> = emptyMap()
    )

    fun unregisterDevice(
        token: String,
        data: Map<String, String> = emptyMap()
    )

}

