package util

import com.fasterxml.jackson.annotation.*
import java.util.logging.*

abstract class SerializableTemplate(
    @JsonIgnore
    val fileName: String
) {

    companion object {

        private val LOG: Logger = Logger.getLogger(SerializableTemplate::class.java.name)
    }

    init {
        load()
    }

    abstract fun load()

    @Synchronized
    fun save() {
        LOG.info("Saving $fileName: $this")
        try {
            SerializationUtil.serializeToFile(this, fileName)
            LOG.info("Saved $fileName: $this")
        } catch (e: Exception) {
            LOG.warning("Error saving $fileName: $e")
        }
    }
}