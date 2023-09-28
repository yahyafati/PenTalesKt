package org.pentales.pentalesrest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PenTalesRestApplication

fun main(args: Array<String>) {
    runApplication<PenTalesRestApplication>(*args)
}
