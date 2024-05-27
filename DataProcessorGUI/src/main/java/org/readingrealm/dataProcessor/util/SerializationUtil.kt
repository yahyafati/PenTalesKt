package org.readingrealm.dataProcessor.util

import com.fasterxml.jackson.databind.*
import java.io.*
import java.nio.file.*
import java.util.logging.*

object SerializationUtil {

    val LOG: Logger = Logger.getLogger(SerializationUtil::class.java.name)

    private val resourcesPath: String
        get() = SerializationUtil::class.java.getResource("/")?.path ?: ""

    val mapper: ObjectMapper = ObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)
    }

    private fun getAbsoluteResourcePathOrCreate(fileName: String): String {
        val path = Paths.get(resourcesPath, fileName).toFile()
        if (!path.exists()) {
            Files.createDirectories(path.parentFile.toPath())
            path.createNewFile()
        }
        return path.absolutePath
    }

    fun getAbsoluteResourcePath(fileName: String): String? {
        val path = Paths.get(resourcesPath, fileName).toFile()
        if (!path.exists()) {
            return null
        }
        return path.absolutePath
    }

    private fun serialize(obj: Any): String {
        return mapper.writeValueAsString(obj)
    }

    @Synchronized
    fun serializeToFile(obj: Any, fileName: String) {
        val path = getAbsoluteResourcePathOrCreate(fileName)
        val file = File(path)
        file.writeText(serialize(obj))
    }

    inline fun <reified T> deserialize(json: String): T {
        return mapper.readValue(json, T::class.java)
    }

    inline fun <reified T> deserializeFromFile(fileName: String): T? {

        val path = getAbsoluteResourcePath(fileName)
        if (path == null) {
            LOG.info("File $fileName not found")
            return null
        }
        val file = File(path)
        LOG.info("Loading $fileName from $path")
        return deserialize(file.readText())
    }
}