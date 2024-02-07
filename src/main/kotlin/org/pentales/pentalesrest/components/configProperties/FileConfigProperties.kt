package org.pentales.pentalesrest.components.configProperties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

@Component
@ConfigurationProperties(prefix = "org.pen-tales.file")
data class FileConfigProperties(
    var upload: UploadProperties = UploadProperties(),
) {

    @Component
    data class UploadProperties(
        var path: String = "",
    )

}
