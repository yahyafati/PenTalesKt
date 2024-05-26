package util

import com.fasterxml.jackson.databind.*
import mainform.*
import settings.*
import java.io.*
import java.nio.file.*
import java.time.*
import java.time.temporal.*
import java.util.concurrent.*
import java.util.logging.*
import java.util.zip.*

class DataHandler private constructor() : Closeable {

    var mainForm: MainForm = MainForm.INSTANCE
    var settingsPanel: SettingsPanel = SettingsPanel.INSTANCE
    var count = 0

    private var fileInputStream: FileInputStream? = null
    private var gzipInputStream: GZIPInputStream? = null
    private var inputStreamReader: InputStreamReader? = null
    private var bufferedReader: BufferedReader? = null

    private var fileOutputStream: FileOutputStream? = null
    private var gzipOutputStream: GZIPOutputStream? = null
    private var outputStreamWriter: OutputStreamWriter? = null
    private var bufferedWriter: BufferedWriter? = null

    private var currentSeek: Long = 0

    private val mapper: ObjectMapper = ObjectMapper()

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
            fileInputStream?.channel?.position(currentSeek)
            gzipInputStream = GZIPInputStream(fileInputStream)
            inputStreamReader = gzipInputStream?.let { InputStreamReader(it) }
            bufferedReader = inputStreamReader?.let { BufferedReader(it) }

            fileOutputStream = FileOutputStream(OUTPUT_FILE, true)
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

        if (ratingsCount > settingsPanel.settingsUIData.minimumRating) {
            return row
        }

        return null
    }

    private fun shouldStopProcessing(): Boolean {
        return !mainForm.mainFormData.isProcessing
    }

    private fun pauseProcessing() {
        try {
            LOG.info("Pausing processing")
            Thread.sleep(
                Duration.of(
                    settingsPanel.settingsUIData.sleepDuration.toLong(),
                    ChronoUnit.MINUTES
                )
            )
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun startProcessing(): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync {
            LOG.info("Starting processing")
            var line: String?

            try {
                while ((bufferedReader?.readLine().also { line = it }) != null) {
                    currentSeek = fileInputStream?.channel?.position() ?: 0
                    if (shouldStopProcessing()) {
                        break
                    }
                    if (settingsPanel.settingsUIData.sleepInterval > 0) {
                        if ((count + 1) % settingsPanel.settingsUIData.sleepInterval == 0) {
                            pauseProcessing()
                        }
                    }
                    count++
                    println("Processing line $count")
                    line?.let { row ->
                        processRow(row)?.let {
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