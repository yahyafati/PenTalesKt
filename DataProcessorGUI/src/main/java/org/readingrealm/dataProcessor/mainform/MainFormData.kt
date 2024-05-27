package org.readingrealm.dataProcessor.mainform

import com.fasterxml.jackson.annotation.*
import org.readingrealm.dataProcessor.util.*
import java.util.logging.*

class MainFormData private constructor(var isAdvancedSettingsVisible: Boolean = false, var filePath: String = "") :
    ISerializable {

    @Deprecated("Use INSTANCE", ReplaceWith("MainFormData.INSTANCE"))
    constructor() : this(false, "")

    var isProcessing: Boolean = false
        @JsonIgnore
        get

    companion object {

        private val FILENAME = Companion::class.java.name + ".json"
        private fun load(): MainFormData {
            val fileName = FILENAME
            LOG.info("Loading ${fileName}: $this")
            return try {
                SerializationUtil.deserializeFromFile<MainFormData>(fileName) ?: return MainFormData(
                    isAdvancedSettingsVisible = false,
                    filePath = ""
                )
            } catch (e: Exception) {
                LOG.warning("Error loading $fileName: $e")
                MainFormData(
                    isAdvancedSettingsVisible = false,
                    filePath = ""
                )
            }
        }

        private val LOG: Logger = Logger.getLogger(MainFormData::class.java.name)
        private var INSTANCE: MainFormData? = null

        val instance: MainFormData
            get() {
                if (INSTANCE == null) {
                    INSTANCE = load()
                }
                return INSTANCE!!
            }

    }

    init {
        LOG.info("Loaded $this")
    }

    override val fileName: String = FILENAME

    override fun toString(): String {
        return "${this.javaClass.name}(isAdvancedSettingsVisible=$isAdvancedSettingsVisible, filePath='$filePath')"
    }
}

