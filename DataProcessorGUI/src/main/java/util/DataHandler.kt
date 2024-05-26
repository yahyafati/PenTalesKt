package util

import com.fasterxml.jackson.databind.*
import mainform.*
import settings.*
import java.io.*
import java.nio.file.*
import java.util.concurrent.*
import java.util.logging.*
import java.util.zip.*

class DataHandler private constructor() : Closeable {

    var mainForm: MainForm = MainForm.instance
    var settingsPanel: SettingsPanel = SettingsPanel.instance
    var count = 0
    var foundCount = 0

    private var fileInputStream: FileInputStream? = null
    private var gzipInputStream: GZIPInputStream? = null
    private var inputStreamReader: InputStreamReader? = null
    private var bufferedReader: BufferedReader? = null

    private var fileOutputStream: FileOutputStream? = null
    private var gzipOutputStream: GZIPOutputStream? = null
    private var outputStreamWriter: OutputStreamWriter? = null
    private var bufferedWriter: BufferedWriter? = null

    private val mapper: ObjectMapper = ObjectMapper()
    var isPaused: Boolean = false
        set(value) {
            field = value
            MainFormService.instance.updateStartButton()
        }

    companion object {

        val LOG: Logger = Logger.getLogger(DataHandler::class.java.name)

        private const val OUTPUT_FILE_NAME: String = "output.json.gz"
        private val OUTPUT_FILE: String = Paths.get(
            DataHandler::class.java.getResource("/")?.path ?: "",
            OUTPUT_FILE_NAME
        ).toFile().absolutePath

        val INSTANCE: DataHandler by lazy { DataHandler() }
    }

    init {
        if (mainForm.mainFormData.filePath.isNotEmpty()) {
            openFile()
        }
    }

    private fun openFile() {
        try {
            fileInputStream = FileInputStream(mainForm.mainFormData.filePath)
            gzipInputStream = GZIPInputStream(fileInputStream)
            inputStreamReader = gzipInputStream?.let { InputStreamReader(it) }
            bufferedReader = inputStreamReader?.let { BufferedReader(it) }

            fileOutputStream = FileOutputStream(OUTPUT_FILE, false)
            gzipOutputStream = GZIPOutputStream(fileOutputStream)
            outputStreamWriter = gzipOutputStream?.let { OutputStreamWriter(it) }
            bufferedWriter = outputStreamWriter?.let { BufferedWriter(it) }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun processRow(line: String): Map<*, *>? {
        val row = SerializationUtil.mapper.readValue(line, Map::class.java)

        val ratingsCount = row.getOrDefault("ratings_count", "0").toString().toIntOrNull() ?: 0

        if (ratingsCount > SettingsData.instance.minimumRating) {
            return row
        }

        return null
    }

    private fun shouldStopProcessing(): Boolean {
        return !mainForm.mainFormData.isProcessing
    }

    private fun pauseProcessing() {
        try {
            isPaused = true
            LOG.info("Pausing processing")
//            var seconds = SettingsData.instance.sleepDuration.toLong() * 60
            var seconds = 10
            while (seconds > 0) {
                if (shouldStopProcessing() || !isPaused) {
                    break
                }

                val formattedTime = String.format(
                    "%02d:%02d",
                    seconds / 60,
                    seconds % 60
                )
                val formattedCount = String.format("%,d", count + 1)

                SettingsService.instance.updateStatusLabel(
                    "$formattedCount records processed. Pausing processing for $formattedTime"
                )
                Thread.sleep(1000)
                seconds--
            }
            isPaused = false
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun startProcessing(): CompletableFuture<String> {
        var justStarted = true
        return CompletableFuture.supplyAsync {
            LOG.info("Starting processing")
            var line: String?

            try {
                while ((bufferedReader?.readLine().also { line = it }) != null) {
                    if (shouldStopProcessing()) {
                        return@supplyAsync "Processing paused or stopped by user"
                    }
//                    if (SettingsData.instance.sleepInterval > 0 && !justStarted) {
//                        if ((count + 1) % SettingsData.instance.sleepInterval == 0) {
//                            pauseProcessing()
//                        }
//                    }
                    justStarted = false
                    count++
                    SettingsService.instance.updateStatusLabel("$foundCount / $count valid")
                    line?.let { row ->
                        processRow(row)?.let {
                            foundCount++
                            bufferedWriter?.write(
                                mapper.writeValueAsString(
                                    it
                                ) + "\n"
                            )
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                println("Processed $count records")
            }

            val formattedPercentage = String.format("%.2f", (foundCount.toDouble() / count.toDouble()) * 100)
            return@supplyAsync "$foundCount / $count ($formattedPercentage%) valid"
        }
    }

    override fun close() {
        LOG.info("Closing DataHandler")
        bufferedReader?.close()
        inputStreamReader?.close()
        gzipInputStream?.close()
        fileInputStream?.close()

        bufferedWriter?.close()
        outputStreamWriter?.close()
        gzipOutputStream?.close()
        fileOutputStream?.close()


        bufferedReader = null
        inputStreamReader = null
        gzipInputStream = null
        fileInputStream = null

        bufferedWriter = null
        outputStreamWriter = null
        gzipOutputStream = null
        fileOutputStream = null
    }
}