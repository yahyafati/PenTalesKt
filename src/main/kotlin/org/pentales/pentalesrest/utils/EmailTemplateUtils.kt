package org.pentales.pentalesrest.utils

import org.slf4j.*
import java.util.*

object EmailTemplateUtils {

    private val LOG = LoggerFactory.getLogger(EmailTemplateUtils::class.java)

    private const val REPLACEABLE_CONTENT = "{{{content}}}"
    private const val REPLACEABLE_IMAGE = "{{{thumbnail}}}"

    fun getBaseTemplate(templateURL: String): String {
        return FileUtil.readResourceFileText("$templateURL/base.html")
    }

    fun createEmailTemplate(baseTemplate: String, content: String, imageURL: String? = null): String {
        var replaced = baseTemplate
            .replace(REPLACEABLE_CONTENT, content)
        if (imageURL != null) {
            val imageTag = "<img src=\"$imageURL\" alt=\"\" className=\"thumbnail\" />"
            replaced = replaced.replace(REPLACEABLE_IMAGE, imageTag)
        } else {
            replaced = replaced.replace(REPLACEABLE_IMAGE, "")
        }
        return replaced
    }

    fun getImageBase64(fileName: String): String {
        val image = FileUtil.readResourceFileBytes("templates/email/images/$fileName")
        return Base64.getEncoder().encodeToString(image)
    }

}