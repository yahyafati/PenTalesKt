package org.pentales.pentalesrest.config.properties

import org.pentales.pentalesrest.utils.*
import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*
import java.io.*

@Component
@ConfigurationProperties(prefix = "org.pen-tales.firebase")
class FirebaseProperties(
    var credentialPath: String = "",
) {

    fun getServiceAccount(): File? {
        return FileUtil.getFile(credentialPath)
    }

    fun getServiceAccountStream(): InputStream {
        return getServiceAccount()?.inputStream()
            ?: throw Exception("Service account file (${credentialPath}) not found")
    }
}
