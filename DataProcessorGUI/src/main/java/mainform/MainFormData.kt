package mainform

import com.fasterxml.jackson.annotation.*
import util.*
import java.util.logging.*

class MainFormData(
    var isAdvancedSettingsVisible: Boolean = false,
    var filePath: String = "",
) : SerializableTemplate("UIData.json") {

    @JsonIgnore
    var isProcessing: Boolean = false

    companion object {

        val LOG: Logger = Logger.getLogger(MainFormData::class.java.name)
    }

    override fun load() {
        LOG.info("Loading $fileName: $this")
        try {
            val data = SerializationUtil.deserializeFromFile<MainFormData>(fileName)
            if (data != null) {
                this.isAdvancedSettingsVisible = data.isAdvancedSettingsVisible
                this.filePath = data.filePath
                LOG.info("Loaded $fileName: $this")
            }
        } catch (e: Exception) {
            LOG.warning("Error loading $fileName: $e")
        }
    }

    override fun toString(): String {
        return "${this.javaClass.name}(isAdvancedSettingsVisible=$isAdvancedSettingsVisible, filePath='$filePath')"
    }
}

