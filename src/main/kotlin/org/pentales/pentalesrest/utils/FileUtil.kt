package org.pentales.pentalesrest.utils

import java.awt.*
import java.awt.image.*
import java.io.*
import java.util.*
import javax.imageio.*

object FileUtil {

    const val MAX_FILE_NAME_LENGTH = 20

    fun getExtension(filename: String, defaultExtension: String = ""): String {
        val i = filename.lastIndexOf('.')
        return (if (i > 0) {
            filename.substring(i + 1)
        } else {
            defaultExtension
        }).trim()
    }

    fun getFilenameWithoutExtension(filename: String): String {
        val i = filename.lastIndexOf('.')
        return if (i > 0) {
            filename.substring(0, i)
        } else {
            filename
        }
    }

    fun getUniqueFilename(fileName: String, extension: String? = null): String {
        val shortFileName = if (fileName.length > MAX_FILE_NAME_LENGTH) {
            fileName.substring(0, MAX_FILE_NAME_LENGTH) + "~"
        } else {
            fileName
        }
        val fileNameWithoutExtension = getFilenameWithoutExtension(shortFileName)
        val fileExtension = extension ?: getExtension(fileName)
        val uniqueFileName = "${fileNameWithoutExtension}_${UUID.randomUUID()}.$fileExtension"
        return uniqueFileName
    }

    fun toByteArray(scaledFile: BufferedImage, formatName: String): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(scaledFile, formatName, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun convertWebpToJpg(inputStream: InputStream): BufferedImage {
        val webpImage = ImageIO.read(inputStream)
        val jpgImage = BufferedImage(webpImage.width, webpImage.height, BufferedImage.TYPE_INT_RGB)
        jpgImage.createGraphics().drawImage(webpImage, 0, 0, Color.WHITE, null)
        return jpgImage
    }

    fun getFile(fileName: String): File? {
        if (fileName.isBlank()) return null

        return if (fileName.startsWith("classpath:")) {
            val path = fileName.replace("classpath:", "")
            val file = File(this::class.java.classLoader.getResource(path)?.file ?: "")
            if (file.exists()) {
                file
            } else {
                null
            }
        } else {
            val file = File(fileName)
            if (file.exists()) {
                file
            } else {
                null
            }
        }
    }

    private fun readResourceFile(resourceName: String): File {
        val fileName = resourceName.replace("classpath:", "")
        val file = getFile("classpath:$fileName") ?: throw FileNotFoundException("File not found: $fileName")
        return file
    }

    fun readResourceFileText(resourceName: String): String {
        val file = readResourceFile(resourceName)
        return file.readText()
    }

    fun readResourceFileBytes(fileName: String): ByteArray {
        val file = readResourceFile(fileName)
        return file.readBytes()
    }

}
