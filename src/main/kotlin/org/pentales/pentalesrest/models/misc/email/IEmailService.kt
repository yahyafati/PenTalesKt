package org.pentales.pentalesrest.models.misc.email

import java.util.concurrent.*

interface IEmailService {

    fun sendEmail(to: String, subject: String, body: String, isHtml: Boolean = false): CompletableFuture<Void>

    fun sendEmailSync(to: String, subject: String, body: String, isHtml: Boolean = false) {
        sendEmail(to, subject, body, isHtml).get()
    }

}