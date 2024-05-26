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

}