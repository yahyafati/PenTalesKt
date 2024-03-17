package org.pentales.pentalesrest

import org.pentales.pentalesrest.components.configProperties.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.context.properties.*
import org.springframework.scheduling.annotation.*

@SpringBootApplication
@EnableConfigurationProperties(SecurityConfigProperties.JwtProperties::class)
@EnableScheduling
class PenTalesRestApplication

fun main(args: Array<String>) {
    runApplication<PenTalesRestApplication>(*args)
}
