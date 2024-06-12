package org.pentales.pentalesrest.models.misc.firebase

import com.google.api.core.*
import com.google.common.util.concurrent.*
import com.google.firebase.messaging.*
import org.springframework.stereotype.Service

@Service
class FirebaseService(
    private val firebaseMessaging: FirebaseMessaging
) {

    companion object {

        val LOG: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(FirebaseService::class.java)
    }

    fun sendMessage(message: Message): ApiFuture<String> {
        return firebaseMessaging.sendAsync(message)
    }

    fun subscribeToTopic(token: String, topic: String): ApiFuture<TopicManagementResponse> {
        return firebaseMessaging.subscribeToTopicAsync(listOf(token), topic)
    }

    fun subscribeToTopics(token: String, topics: List<String>) {
        topics.forEach { topic ->
            subscribeToTopic(token, topic)
                .addListener(
                    {
                        LOG.info("Subscribed to topic $topic")
                    },
                    MoreExecutors.directExecutor()
                )
        }
    }

    fun unsubscribeFromTopic(token: String, topic: String): ApiFuture<TopicManagementResponse> {
        return firebaseMessaging.unsubscribeFromTopicAsync(listOf(token), topic)
    }

}