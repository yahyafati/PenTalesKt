package org.pentales.pentalesrest.models.misc.email

import java.util.concurrent.*

interface IEmailService {

    fun sendEmail(to: String, subject: String, body: String, isHtml: Boolean = true): CompletableFuture<Void>

    fun sendEmailSync(to: String, subject: String, body: String, isHtml: Boolean = true) {
        sendEmail(to, subject, body, isHtml).get()
    }

}