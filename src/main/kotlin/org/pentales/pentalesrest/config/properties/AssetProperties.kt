package org.pentales.pentalesrest.config.properties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

@Component
@ConfigurationProperties(prefix = "org.pen-tales.file")
data class AssetProperties(
    var upload: UploadProperties = UploadProperties(),
) {

    @Component
    data class UploadProperties(
        var path: String = "",
        var allowedExtensions: List<String> = emptyList(),
    )

}
