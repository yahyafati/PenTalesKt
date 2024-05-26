package settings

import mainform.*
import java.util.logging.*

class SettingsService private constructor() {

    fun saveSettings() {
        val settingsPanel = SettingsPanel.instance

        println("Loading settings: ${settingsPanel.hashCode()}")
        println(
            "Text: ${SettingsPanel.instance.minimumRatingTextField.text}" +
                    " ${SettingsPanel.instance.sleepIntervalTextField.text}" +
                    " ${SettingsPanel.instance.sleepDurationTextField.text}" +
                    " ${SettingsPanel.instance.startFromTextField.text}" +
                    " ${SettingsPanel.instance.endAtTextField.text}"
        )

        val data = SettingsData.instance

        data.minimumRating = SettingsPanel.instance.minimumRatingTextField.text.toIntOrNull() ?: DEFAULT_MINIMUM_RATING
        data.sleepInterval = SettingsPanel.instance.sleepIntervalTextField.text.toIntOrNull() ?: DEFAULT_SLEEP_INTERVAL
        data.sleepDuration = SettingsPanel.instance.sleepDurationTextField.text.toIntOrNull() ?: DEFAULT_SLEEP_DURATION
        data.startFrom = SettingsPanel.instance.startFromTextField.text.toIntOrNull() ?: DEFAULT_START_FROM
        data.endAt = SettingsPanel.instance.endAtTextField.text.toIntOrNull() ?: DEFAULT_END_AT

        println("Saving settings: $settingsPanel")
        data.save()

    }

    fun updateStatusLabel(value: String, prefix: String = "Status: ") {
        MainForm.instance.statusLabel.text = "$prefix$value"
        MainForm.instance.statusLabel.repaint()
    }

    init {
        LOG.info("SettingsService: $this")
    }

    companion object {

        const val DEFAULT_MINIMUM_RATING = 2000
        const val DEFAULT_SLEEP_INTERVAL = 10000
        const val DEFAULT_SLEEP_DURATION = 1
        const val DEFAULT_START_FROM = 0
        const val DEFAULT_END_AT = 100

        private var INSTANCE: SettingsService? = null

        private val LOG = Logger.getLogger(SettingsService::class.java.name)

        val instance: SettingsService
            get() {
                if (INSTANCE == null) {
                    LOG.info("Creating new SettingsService")
                    INSTANCE = SettingsService()
                }
                return INSTANCE as SettingsService
            }

    }

}
