package org.readingrealm.dataProcessor.mainform

import java.awt.event.*
import java.util.logging.*

class MainFormListeners private constructor() {

    companion object {

        val LOG: Logger = Logger.getLogger(MainFormListeners::class.java.name)

        private var INSTANCE: MainFormListeners? = null

        val instance: MainFormListeners
            get() {
                if (INSTANCE == null) {
                    INSTANCE = MainFormListeners()
                }
                return INSTANCE!!
            }

    }

    init {
        LOG.info("Loaded $this")
    }

    fun toggleAdvancedSettingsListener(): ActionListener {
        return ActionListener {
            LOG.info("Toggle advanced settings listener triggered")
            MainFormService.instance.toggleAdvancedSettings()
        }
    }

    fun openFileListener(): ActionListener {
        return ActionListener {
            LOG.info("Open file listener triggered")
            MainFormService.instance.openFile()
        }
    }

    fun windowListener(): WindowAdapter {
        return object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                LOG.info("Window closing listener triggered")
                MainFormService.instance.formClosing()
            }
        }
    }

    fun startProcessingListener(): ActionListener {
        return ActionListener {
            LOG.info("Start processing listener triggered")
            MainFormService.instance.startProcessing()
        }
    }

}