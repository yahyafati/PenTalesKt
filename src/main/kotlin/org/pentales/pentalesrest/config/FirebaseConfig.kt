package org.pentales.pentalesrest.config

import com.google.auth.oauth2.*
import com.google.firebase.*
import com.google.firebase.messaging.*
import org.pentales.pentalesrest.components.configProperties.*
import org.springframework.context.annotation.*
import org.springframework.stereotype.*

@Component
class FirebaseConfig(
    private val firebaseProperties: FirebaseConfigProperties
) {

    companion object {

        val LOG: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(FirebaseConfig::class.java)
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging {
        val instance = FirebaseMessaging.getInstance(firebaseApp)
        LOG.info("FirebaseMessaging instance: ${instance.javaClass.name}")
        return instance
    }

    @Bean
    fun firebaseApp(credentials: GoogleCredentials): FirebaseApp {
        val options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .build()

        val initialized = FirebaseApp.initializeApp(options)
        LOG.info("FirebaseApp initialized: ${initialized.name}")
        return initialized
    }

    @Bean
    fun googleCredentials(): GoogleCredentials {
        LOG.info("Firebase credential path: ${firebaseProperties.credentialPath}")
        if (firebaseProperties.credentialPath.isNotBlank()) {
            firebaseProperties.getServiceAccountStream()
                .use {
                    return GoogleCredentials.fromStream(it)
                }
        } else {
            // Use standard credentials chain. Useful when running inside GKE
            return GoogleCredentials.getApplicationDefault()
        }
    }

}