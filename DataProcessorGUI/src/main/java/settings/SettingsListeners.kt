package settings

import java.awt.event.*

class SettingsListeners {

    fun saveSettingsListener(): ActionListener {
        return ActionListener { e ->
            SettingsService.INSTANCE.saveSettings()
        }
    }

    companion object {

        val INSTANCE: SettingsListeners by lazy { SettingsListeners() }
    }
}