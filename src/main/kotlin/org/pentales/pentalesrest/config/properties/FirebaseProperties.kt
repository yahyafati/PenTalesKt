package org.pentales.pentalesrest.config.properties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*
import java.io.*

@Component
@ConfigurationProperties(prefix = "org.pen-tales.firebase")
class FirebaseProperties(
    var credentialPath: String = "",
) {

    fun getServiceAccount(): File? {
//        this::class.java.classLoader.getResourceAsStream(credentialPath)

        return if (credentialPath.isNotBlank()) {
            if (credentialPath.startsWith("classpath:")) {
                val path = credentialPath.replace("classpath:", "")
                val file = File(this::class.java.classLoader.getResource(path)?.file ?: "")
                if (file.exists()) {
                    file
                } else {
                    null
                }
            } else {
                val file = File(credentialPath)
                if (file.exists()) {
                    file
                } else {
                    null
                }
            }
        } else {
            null
        }
    }

    fun getServiceAccountStream(): InputStream {
        return getServiceAccount()?.inputStream()
            ?: throw Exception("Service account file (${credentialPath}) not found")
    }
}
