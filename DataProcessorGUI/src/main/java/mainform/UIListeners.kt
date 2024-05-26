package mainform

import java.awt.event.*
import java.util.logging.*

class UIListeners private constructor() {

    companion object {

        val LOG: Logger = Logger.getLogger(UIListeners::class.java.name)

        private var INSTANCE: UIListeners? = null

        val instance: UIListeners
            get() {
                if (INSTANCE == null) {
                    INSTANCE = UIListeners()
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
            UIService.instance.toggleAdvancedSettings()
        }
    }

    fun openFileListener(): ActionListener {
        return ActionListener {
            LOG.info("Open file listener triggered")
            UIService.instance.openFile()
        }
    }

    fun windowListener(): WindowAdapter {
        return object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                LOG.info("Window closing listener triggered")
                UIService.instance.formClosing()

            }
        }
    }

    fun startProcessingListener(): ActionListener {
        return ActionListener {
            LOG.info("Start processing listener triggered")
            UIService.instance.startProcessing()
        }
    }

}