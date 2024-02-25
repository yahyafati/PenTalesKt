package org.pentales.pentalesrest.components.configProperties

import org.slf4j.*
import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

@Component
@ConfigurationProperties(prefix = "org.pen-tales.security.cors")
data class CorsConfigProperties(
    var allowedOrigins: List<String> = emptyList(),
    var allowedMethods: List<String> = emptyList(),
    var allowedHeaders: List<String> = emptyList(),
    var exposedHeaders: List<String> = emptyList(),
    var allowCredentials: Boolean = false,
    var maxAge: Long = 0,
) {

    companion object {

        val LOG: Logger = LoggerFactory.getLogger(CorsConfigProperties::class.java)
    }

    init {
        LOG.info("CorsConfigProperties: ${toString()}")
    }

    final override fun toString(): String {
        return "CorsConfigProperties(" +
                "allowedOrigins=$allowedOrigins, " +
                "allowedMethods=$allowedMethods, " +
                "allowedHeaders=$allowedHeaders, " +
                "exposedHeaders=$exposedHeaders, " +
                "allowCredentials=$allowCredentials, " +
                "maxAge=$maxAge" +
                ")"
    }
}
