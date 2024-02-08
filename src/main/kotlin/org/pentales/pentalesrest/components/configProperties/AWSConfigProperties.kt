package org.pentales.pentalesrest.components.configProperties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

@Component
@ConfigurationProperties(prefix = "org.pen-tales.aws")
data class AWSConfigProperties(
    var s3: S3Properties = S3Properties(),
) {

    @Component
    data class S3Properties(
        var region: String = "",
        var bucket: String = "",
    )

}
