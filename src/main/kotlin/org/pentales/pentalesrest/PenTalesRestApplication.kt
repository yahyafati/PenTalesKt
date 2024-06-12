package org.pentales.pentalesrest

import org.pentales.pentalesrest.config.properties.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.context.properties.*
import org.springframework.scheduling.annotation.*

@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.JwtProperties::class)
@EnableScheduling
class PenTalesRestApplication

fun main(args: Array<String>) {
    runApplication<PenTalesRestApplication>(*args)
}
