package org.pentales.pentalesrest.services.impl

import com.google.firebase.messaging.*
import org.pentales.pentalesrest.config.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.slf4j.*
import org.springframework.stereotype.*

@Service
class PushNotificationService(
    private val firebaseService: FirebaseService
) : IPushNotificationService {

    companion object {

        val LOG: Logger = LoggerFactory.getLogger(PushNotificationService::class.java)
    }

    override fun sendPushNotificationToAllUsers(notification: Notification) {
        val message = Message.builder()
            .setNotification(
                notification
            )
            .setTopic("all")
            .build()

        firebaseService.sendMessage(message)
    }

    override fun sendPushNotificationToUser(notification: Notification, userId: Long) {
        val message = Message.builder()
            .setNotification(
                notification
            )
            .setTopic("user_$userId")
            .build()

        firebaseService.sendMessage(message)
    }

    override fun sendPushNotificationToUsers(notification: Notification, userIds: List<Long>) {
        // TODO: There is a batch limit of 500 messages
        if (userIds.size > 20) {
            throw IllegalArgumentException("The number of users (${userIds.size}) is too high")
        }
        userIds.forEach { userId ->
            val message = Message.builder()
                .setNotification(
                    notification
                )
                .setTopic("user_$userId")
                .build()

            firebaseService.sendMessage(message)
        }
    }

    override fun sendPushNotificationToToken(notification: Notification, token: String) {
        val message = Message.builder()
            .setNotification(
                notification
            )
            .setToken(token)
            .build()

        firebaseService.sendMessage(message)
    }

    override fun registerDevice(token: String, user: User) {
        firebaseService.subscribeToTopics(token, listOf("all", "user_${user.id}"))
        LOG.info("Device registered for user ${user.username}")
    }

    override fun unregisterDevice(token: String) {
        firebaseService.unsubscribeFromTopic("all", token)
        LOG.info("Device unregistered")
    }
}