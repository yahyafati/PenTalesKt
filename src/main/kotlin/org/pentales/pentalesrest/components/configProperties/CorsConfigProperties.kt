package org.pentales.pentalesrest.components.configProperties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

@Component
@ConfigurationProperties(prefix = "org.pen-tales.cors")
data class CorsConfigProperties(
    var allowedOrigins: List<String> = emptyList(),
    var allowedMethods: List<String> = emptyList(),
    var allowedHeaders: List<String> = emptyList(),
    var exposedHeaders: List<String> = emptyList(),
    var allowCredentials: Boolean = false,
    var maxAge: Long = 0,
)
