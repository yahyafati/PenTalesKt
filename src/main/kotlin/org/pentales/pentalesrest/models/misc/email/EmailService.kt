package org.pentales.pentalesrest.models.misc.email

import org.slf4j.*
import org.springframework.mail.javamail.*
import org.springframework.stereotype.*
import java.util.concurrent.*

@Service
class EmailService(
    private val mailSender: JavaMailSender
) : IEmailService {

    companion object {

        private val LOG = LoggerFactory.getLogger(EmailService::class.java)
    }

    init {
        LOG.info("EmailService initialized")
    }

    final override fun sendEmail(to: String, subject: String, body: String, isHtml: Boolean): CompletableFuture<Void> {
        return CompletableFuture.runAsync {
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(body, isHtml)
            mailSender.send(message)
        }
    }
}