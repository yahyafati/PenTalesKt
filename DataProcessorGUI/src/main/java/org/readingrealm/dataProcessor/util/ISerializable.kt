package org.readingrealm.dataProcessor.util

import java.util.logging.*

interface ISerializable {

    companion object {

        private val LOG: Logger = Logger.getLogger(ISerializable::class.java.name)
    }

    val fileName: String

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