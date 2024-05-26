package mainform

import java.awt.*
import java.io.*

class UIService private constructor() {

    fun toggleAdvancedSettings() {
        MainForm.INSTANCE.uiData.isAdvancedSettingsVisible = !MainForm.INSTANCE.uiData.isAdvancedSettingsVisible
        MainForm.INSTANCE.settingsToggleButton.text =
            if (MainForm.INSTANCE.uiData.isAdvancedSettingsVisible) "Close Advanced Settings" else "Open Advanced Settings"
        refreshUI()
    }

    fun refreshUI() {
        MainForm.INSTANCE.invalidate()
        MainForm.INSTANCE.repaint()

    }

    fun openFile() {
        val allowedFileExtensions = arrayOf("gz")
        val fileDialog = FileDialog(MainForm.INSTANCE, "Choose a file", FileDialog.LOAD)
        fileDialog.filenameFilter = FilenameFilter { dir, name ->
            allowedFileExtensions.any { name.endsWith(".$it") }
        }
        fileDialog.isVisible = true
        val file = fileDialog.file
        if (file == null || file.isEmpty()) {
            return
        }

        MainForm.INSTANCE.uiData.filePath = fileDialog.directory + file
        refreshUI()
    }

    fun formClosing() {
        MainForm.INSTANCE.uiData.save()
    }

    fun startProcessing() {

    }

    companion object {

        val INSTANCE: UIService by lazy { UIService() }
    }
}