package mainform

import settings.*
import util.*
import java.awt.*
import java.io.*
import java.util.logging.*

class UIService private constructor() {

    init {
        LOG.info("Loaded $this")
    }

    fun toggleAdvancedSettings() {
        MainForm.instance.mainFormData.isAdvancedSettingsVisible =
            !MainForm.instance.mainFormData.isAdvancedSettingsVisible
        MainForm.instance.settingsToggleButton.text =
            if (MainForm.instance.mainFormData.isAdvancedSettingsVisible) "Close Advanced Settings" else "Open Advanced Settings"
        refreshUI()
    }

    fun refreshUI() {
        MainForm.instance.invalidate()
        MainForm.instance.repaint()

    }

    fun openFile() {
        val allowedFileExtensions = arrayOf("gz")
        val fileDialog = FileDialog(MainForm.instance, "Choose a file", FileDialog.LOAD)
        fileDialog.filenameFilter = FilenameFilter { dir, name ->
            allowedFileExtensions.any { name.endsWith(".$it") }
        }
        fileDialog.isVisible = true
        val file = fileDialog.file
        if (file == null || file.isEmpty()) {
            return
        }

        MainForm.instance.mainFormData.filePath = fileDialog.directory + file
        refreshUI()
    }

    fun formClosing() {
        MainForm.instance.mainFormData.save()
        SettingsService.instance.saveSettings()
        DataHandler.INSTANCE.close()
    }

    fun startProcessing() {
        if (MainForm.instance.mainFormData.isProcessing) {
            MainForm.instance.mainFormData.isProcessing = false
            refreshUI()
            return
        }

        MainForm.instance.mainFormData.isProcessing = true
        DataHandler.INSTANCE.startProcessing().thenRun {
            MainForm.instance.mainFormData.isProcessing = false
            refreshUI()
        }
        refreshUI()
    }

    companion object {

        private val LOG = Logger.getLogger(UIService::class.java.name)
        private var INSTANCE: UIService? = null

        val instance: UIService
            get() {
                if (INSTANCE == null) {
                    INSTANCE = UIService()
                }
                return INSTANCE!!
            }
    }
}