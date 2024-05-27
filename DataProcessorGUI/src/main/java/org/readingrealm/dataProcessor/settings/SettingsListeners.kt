package org.readingrealm.dataProcessor.settings

import java.awt.event.*
import java.util.logging.*

class SettingsListeners {

    init {
        LOG.info("SettingsListeners: $this")
    }

    fun saveSettingsListener(): ActionListener {
        return ActionListener { e ->
            SettingsService.instance.saveSettings()
        }
    }

    companion object {

        private var INSTANCE: SettingsListeners? = null

        private val LOG = Logger.getLogger(SettingsListeners::class.java.name)

        val instance: SettingsListeners
            get() {
                if (INSTANCE == null) {
                    LOG.info("Creating new SettingsListeners")
                    INSTANCE = SettingsListeners()
                }
                return INSTANCE as SettingsListeners
            }
    }

}