package mainform

import settings.*
import util.*
import java.awt.*
import java.io.*
import java.util.zip.*

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
        var line: String?
        val resultList = mutableListOf<Map<*, *>>()
        var count = 0
        try {
            FileInputStream(
                MainForm.INSTANCE.uiData.filePath
            ).use { fis ->
                GZIPInputStream(fis).use { gzis ->
                    InputStreamReader(gzis).use { reader ->
                        BufferedReader(reader).use { br ->
                            while ((br.readLine().also { line = it }) != null) {
                                count++
                                val result = SerializationUtil.mapper.readValue(line, Map::class.java)
                                val ratingsCount =
                                    result.getOrDefault("ratings_count", "0").toString().toIntOrNull() ?: 0
                                println("ratingsCount: $ratingsCount")

                                if (ratingsCount > SettingsPanel.INSTANCE.settingsUIData.minimumRating) {
                                    resultList.add(result)
                                }

                                if (resultList.size >= 100) {
                                    println("Already processed 10 records after just $count records, stopping now")
                                    break
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            println("Processed $count records")
            println("Result list size: ${resultList.size}")
        }
    }

    companion object {

        val INSTANCE: UIService by lazy { UIService() }
    }
}