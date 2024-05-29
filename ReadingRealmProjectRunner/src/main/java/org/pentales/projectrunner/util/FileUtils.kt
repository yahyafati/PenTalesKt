package org.pentales.projectrunner.util

import java.io.*
import java.nio.file.*
import java.util.logging.*

object FileUtils {

    private val LOG = Logger.getLogger(FileUtils::class.java.name)

    fun copyResourceToFile(resource: String, toFileName: String) {
        val file = File(toFileName)
        val parentDirectory = file.parentFile
        Files.createDirectories(parentDirectory.toPath())
        if (file.exists()) {
            LOG.info("File $toFileName already exists")
            return
        }

        LOG.info("Copying $resource file to backend directory")
        val inputStream = javaClass.getResourceAsStream(resource)
        val outputStream = FileOutputStream(toFileName)
        inputStream.use { input ->
            outputStream.use { output ->
                input?.copyTo(output) ?: {
                    LOG.severe("Could not copy $resource file to $parentDirectory directory")
                    throw IOException("Could not copy $resource file to $parentDirectory directory")
                }
            }
        }

    }

}