package org.pentales.pentalesrest.utils

import java.awt.image.*
import java.io.*
import java.util.*
import javax.imageio.*

object FileUtil {

    const val MAX_FILE_NAME_LENGTH = 20

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

    fun getUniqueFilename(fileName: String): String {
        val shortFileName = if (fileName.length > MAX_FILE_NAME_LENGTH) {
            fileName.substring(0, MAX_FILE_NAME_LENGTH) + "~"
        } else {
            fileName
        }
        val fileNameWithoutExtension = getFilenameWithoutExtension(shortFileName)
        val extension = getExtension(fileName)
        val uniqueFileName = "${fileNameWithoutExtension}_${UUID.randomUUID()}.$extension"
        return uniqueFileName
    }

    fun toByteArray(scaledFile: BufferedImage, formatName: String): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(scaledFile, formatName, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}
