package mainform

import com.fasterxml.jackson.annotation.*
import util.*
import java.util.logging.*

class UIData(
    isAdvancedSettingsVisible: Boolean = false,
    filePath: String = "",
) : ISerializable {

    var isAdvancedSettingsVisible by ChangeDelegate(isAdvancedSettingsVisible, this::save)
    var filePath by ChangeDelegate(filePath, this::save)

    companion object {

        val LOG: Logger = Logger.getLogger(UIData::class.java.name)
    }

    @JsonIgnore
    override val fileName = "UIData.json"

    init {
        LOG.info("UIData initialized: $this")
        load()
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

    @Synchronized
    override fun save() {
        super.save()
    }

    override fun toString(): String {
        return "UIData(isAdvancedSettingsVisible=$isAdvancedSettingsVisible, filePath='$filePath')"
    }
}

