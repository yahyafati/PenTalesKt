package settings

class SettingsService private constructor() {

    fun saveSettings() {
        
        SettingsPanel.INSTANCE.settingsUIData.save()
    }

    companion object {

        val INSTANCE: SettingsService = SettingsService()

    }

}
