package org.pentales.pentalesrest

import org.pentales.pentalesrest.components.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.context.properties.*

@SpringBootApplication
@EnableConfigurationProperties(SecurityConfigProperties.JwtProperties::class)
class PenTalesRestApplication

fun main(args: Array<String>) {
    runApplication<PenTalesRestApplication>(*args)
}
