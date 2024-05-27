package org.readingrealm.dataProcessor.util

import com.fasterxml.jackson.databind.*
import org.jsoup.*
import org.readingrealm.dataProcessor.mainform.*
import org.readingrealm.dataProcessor.settings.*
import java.io.*
import java.nio.file.*
import java.util.concurrent.*
import java.util.logging.*
import java.util.zip.*

class DataHandler private constructor() : Closeable {

    var mainForm: MainForm = MainForm.instance
    var settingsPanel: SettingsPanel = SettingsPanel.instance
    private var count = 0
    private var foundCount = 0
    private var consecutiveFetchFailCount = 0
    private var pauseFetchUntil = 0L
    private var skippedBooks = mutableListOf<String>()

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
        val row = SerializationUtil.mapper.readValue(line, Map::class.java).toMutableMap()

        val ratingsCount = row.getOrDefault("ratings_count", "0").toString().toIntOrNull() ?: 0

        if (ratingsCount > SettingsData.instance.minimumRating) {
            row["image_url"] = getOriginalImageURL(row)
            return row.toMap()
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
            var seconds = SettingsData.instance.sleepDuration.toLong() * 60
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

    private fun getOriginalImageURL(row: Map<*, *>): String {
        if (MainFormData.instance.isProcessing) {
            SettingsService.instance.updateStatusLabel("$foundCount / $count: Fetching image for book ${row["book_id"]}...")
        }


        if (consecutiveFetchFailCount > 10 && pauseFetchUntil < System.currentTimeMillis()) {
            pauseFetchUntil = System.currentTimeMillis() + 60_000
        }

        if (pauseFetchUntil > System.currentTimeMillis()) {
            consecutiveFetchFailCount = 0
            LOG.warning("Too many consecutive fetch failures. Pausing for 1 minute")
            skippedBooks.add(row.getOrDefault("book_id", "").toString())
            return row.getOrDefault("image_url", "").toString()
        }
        
        pauseFetchUntil = 0

        val urlTemplate = "https://www.goodreads.com/book/show/%s"
        val defaultImageURL = row.getOrDefault("image_url", "").toString()
        val id = row.getOrDefault("book_id", "").toString()
        if (id.isEmpty()) {
            return defaultImageURL
        }

        return try {
            val doc = Jsoup.connect(urlTemplate.format(id)).get()
            val img = doc.select("div.BookCover__image img").first()
            val src = img?.attr("src")

            consecutiveFetchFailCount = 0
            src ?: defaultImageURL
        } catch (e: Exception) {
            consecutiveFetchFailCount++
            skippedBooks.add(id)
            LOG.warning("Failed to fetch image for book $id")
            defaultImageURL
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
                        return@supplyAsync "PAUSED! $foundCount / $count valid"
                    }
                    if (SettingsData.instance.sleepInterval > 0 && !justStarted) {
                        if ((count + 1) % SettingsData.instance.sleepInterval == 0) {
                            pauseProcessing()
                        }
                    }
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
            return@supplyAsync "Completed! $foundCount / $count ($formattedPercentage%) valid"
        }
    }

    override fun close() {
//        Save Skipped Books to File
        val skippedBooksFile = Paths.get(
            DataHandler::class.java.getResource("/")?.path ?: "",
            "skipped_books.csv"
        ).toFile().absolutePath

        File(skippedBooksFile).writeText(skippedBooks.joinToString("\n"))

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