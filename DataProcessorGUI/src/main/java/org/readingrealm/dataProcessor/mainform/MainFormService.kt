package org.readingrealm.dataProcessor.mainform

import org.readingrealm.dataProcessor.settings.*
import org.readingrealm.dataProcessor.util.*
import java.awt.*
import java.io.*
import java.util.logging.*
import javax.swing.*

class MainFormService private constructor() {

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

    fun updateStartButton() {
        MainForm.instance.startButton.text =
            if (!MainForm.instance.mainFormData.isProcessing) "Start Processing" else
                if (DataHandler.INSTANCE.isPaused) "Resume Processing" else "Pause Processing"
        MainForm.instance.startButton.repaint()
    }

    fun formClosing() {
        MainForm.instance.mainFormData.save()
        SettingsService.instance.saveSettings()
        DataHandler.INSTANCE.close()
    }

    fun setProcessing(value: Boolean) {
        MainForm.instance.mainFormData.isProcessing = value
        updateStartButton()
        refreshUI()
    }

    fun startProcessing() {
        if (MainForm.instance.mainFormData.isProcessing) {
            setProcessing(false)
            return
        }

        setProcessing(true)
        DataHandler.INSTANCE.startProcessing().thenApply {
            JOptionPane.showMessageDialog(
                MainForm.instance,
                it,
                "Processing Complete",
                JOptionPane.INFORMATION_MESSAGE,
                null
            )
            setProcessing(false)
            refreshUI()
        }
        refreshUI()
    }

    companion object {

        private val LOG = Logger.getLogger(MainFormService::class.java.name)
        private var INSTANCE: MainFormService? = null

        val instance: MainFormService
            get() {
                if (INSTANCE == null) {
                    INSTANCE = MainFormService()
                }
                return INSTANCE!!
            }
    }
}