package mainform

import java.awt.event.*
import java.util.logging.*

class UIListeners private constructor() {

    companion object {

        val LOG: Logger = Logger.getLogger(UIListeners::class.java.name)

        val INSTANCE: UIListeners by lazy { UIListeners() }
    }

    fun toggleAdvancedSettingsListener(): ActionListener {
        return ActionListener {
            LOG.info("Toggle advanced settings listener triggered")
            UIService.INSTANCE.toggleAdvancedSettings()
        }
    }

    fun openFileListener(): ActionListener {
        return ActionListener {
            LOG.info("Open file listener triggered")
            UIService.INSTANCE.openFile()
        }
    }

    fun windowListener(): WindowAdapter {
        return object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                LOG.info("Window closing listener triggered")
                UIService.INSTANCE.formClosing()
            }
        }
    }

    fun startProcessingListener(): ActionListener {
        return ActionListener {
            LOG.info("Start processing listener triggered")
            UIService.INSTANCE.startProcessing()
        }
    }

}