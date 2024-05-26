package mainform

import java.awt.*

class UIService private constructor() {

    fun toggleAdvancedSettings() {
        MainForm.INSTANCE.uiData.isAdvancedSettingsVisible = !MainForm.INSTANCE.uiData.isAdvancedSettingsVisible
        if (MainForm.INSTANCE.uiData.isAdvancedSettingsVisible) {
            MainForm.INSTANCE.centerPanel.add(MainForm.INSTANCE.settingsPanel, BorderLayout.CENTER)
        } else {
            MainForm.INSTANCE.centerPanel.remove(MainForm.INSTANCE.settingsPanel)
        }
        MainForm.INSTANCE.settingsToggleButton.text =
            if (MainForm.INSTANCE.uiData.isAdvancedSettingsVisible) "Close Advanced Settings" else "Open Advanced Settings"
        refreshUI()
    }

    fun refreshUI() {
        MainForm.INSTANCE.invalidate()
        MainForm.INSTANCE.repaint()

    }

    companion object {

        val INSTANCE: UIService by lazy { UIService() }
    }
}