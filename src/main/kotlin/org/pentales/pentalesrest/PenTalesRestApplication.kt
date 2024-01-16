package org.pentales.pentalesrest

import de.codecentric.boot.admin.server.config.*
import org.pentales.pentalesrest.config.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.context.properties.*

@SpringBootApplication
@EnableConfigurationProperties(SecurityConfigProperties.JwtProperties::class)
@EnableAdminServer
class PenTalesRestApplication

fun main(args: Array<String>) {
    runApplication<PenTalesRestApplication>(*args)
}
