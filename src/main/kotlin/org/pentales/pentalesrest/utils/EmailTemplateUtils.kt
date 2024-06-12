package org.pentales.pentalesrest.utils

import java.util.*

object EmailTemplateUtils {

    private val BASE_TEMPLATE = FileUtil.readResourceFileText("templates/email/base.html")
    private const val REPLACEABLE_CONTENT = "{{{content}}}"
    private const val REPLACEABLE_IMAGE = "{{{thumbnail}}}"

    fun createEmailTemplate(content: String, imageURL: String? = null): String {
        var replaced = BASE_TEMPLATE
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