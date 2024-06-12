package org.pentales.pentalesrest.models.misc.pushNotification

import com.google.firebase.messaging.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.misc.firebase.*
import org.slf4j.*
import org.springframework.stereotype.*

@Service
class PushNotificationService(
    private val firebaseService: FirebaseService
) : IPushNotificationService {

    companion object {

        val LOG: Logger = LoggerFactory.getLogger(PushNotificationService::class.java)
    }

    override fun sendPushNotificationToAllUsers(
        action: IPushNotificationService.ActionType,
        data: Map<String, String>
    ) {
        val message = Message.builder()
            .setTopic("all")
            .putAllData(data)
            .putData("action", action.name)
            .build()

        firebaseService.sendMessage(message)
    }

    override fun sendPushNotificationToUser(
        action: IPushNotificationService.ActionType,
        userId: Long,
        data: Map<String, String>
    ) {
        val message = Message.builder()
            .putAllData(data)
            .putData("action", action.name)
            .setTopic("user_$userId")
            .build()
        LOG.info("Sending push notification to user $userId")
        firebaseService.sendMessage(message)
    }

    override fun sendPushNotificationToUsers(
        action: IPushNotificationService.ActionType,
        userIds: List<Long>,
        data: Map<String, String>
    ) {
        // TODO: There is a batch limit of 500 messages
        if (userIds.size > 20) {
            throw IllegalArgumentException("The number of users (${userIds.size}) is too high")
        }
        userIds.forEach { userId ->
            val message = Message.builder()
                .setTopic("user_$userId")
                .putAllData(data)
                .putData("action", action.name)
                .build()

            firebaseService.sendMessage(message)
        }
    }

    override fun sendPushNotificationToToken(
        action: IPushNotificationService.ActionType,
        token: String,
        data: Map<String, String>
    ) {
        val message = Message.builder()
            .putAllData(data)
            .putData("action", action.name)
            .setToken(token)
            .build()

        firebaseService.sendMessage(message)
    }

    override fun registerDevice(token: String, user: User, data: Map<String, String>) {
        firebaseService.subscribeToTopics(token, listOf("all", "user_${user.id}"))
        LOG.info("Device registered for user ${user.username}")
    }

    override fun unregisterDevice(token: String, data: Map<String, String>) {
        firebaseService.unsubscribeFromTopic("all", token)
        LOG.info("Device unregistered")
    }
}