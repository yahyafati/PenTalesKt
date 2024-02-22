package org.pentales.pentalesrest.utils

object FileUtil {

    fun getExtension(filename: String, defaultExtension: String = ""): String {
        val i = filename.lastIndexOf('.')
        return if (i > 0) {
            filename.substring(i + 1)
        } else {
            defaultExtension
        }
    }

    fun getFilenameWithoutExtension(filename: String): String {
        val i = filename.lastIndexOf('.')
        return if (i > 0) {
            filename.substring(0, i)
        } else {
            filename
        }
    }
}
