package mainform

import util.*
import java.util.logging.*

class UIData(
    var isAdvancedSettingsVisible: Boolean = false,
    var filePath: String = "",
) : SerializableTemplate("UIData.json") {

    companion object {

        val LOG: Logger = Logger.getLogger(UIData::class.java.name)
    }

    override fun load() {
        LOG.info("Loading $fileName: $this")
        try {
            val data = SerializationUtil.deserializeFromFile<UIData>(fileName)
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
        return "UIData(isAdvancedSettingsVisible=$isAdvancedSettingsVisible, filePath='$filePath')"
    }
}

