package mainform

import util.*
import java.awt.*
import java.io.*

class UIService private constructor() {

    fun toggleAdvancedSettings() {
        MainForm.INSTANCE.mainFormData.isAdvancedSettingsVisible =
            !MainForm.INSTANCE.mainFormData.isAdvancedSettingsVisible
        MainForm.INSTANCE.settingsToggleButton.text =
            if (MainForm.INSTANCE.mainFormData.isAdvancedSettingsVisible) "Close Advanced Settings" else "Open Advanced Settings"
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

        MainForm.INSTANCE.mainFormData.filePath = fileDialog.directory + file
        refreshUI()
    }

    fun formClosing() {
        MainForm.INSTANCE.mainFormData.save()
        DataHandler.INSTANCE.close()
    }

    fun startProcessing() {
        if (MainForm.INSTANCE.mainFormData.isProcessing) {
            MainForm.INSTANCE.mainFormData.isProcessing = false
            refreshUI()
            return
        }

        MainForm.INSTANCE.mainFormData.isProcessing = true
        DataHandler.INSTANCE.startProcessing().thenRun {
            MainForm.INSTANCE.mainFormData.isProcessing = false
            refreshUI()
        }
        refreshUI()
    }

    companion object {

        val INSTANCE: UIService by lazy { UIService() }
    }
}