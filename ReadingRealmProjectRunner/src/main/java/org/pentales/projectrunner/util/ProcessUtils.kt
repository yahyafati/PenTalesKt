package org.pentales.projectrunner.util

import org.pentales.projectrunner.mainform.*
import java.io.*
import java.nio.file.*
import java.util.logging.*

object ProcessUtils {

    private val LOG = Logger.getLogger(MainFormListeners::class.java.name)

    interface ProcessOutputListener {

        fun onOutput(line: String?)
    }

    private val logListener = object : ProcessOutputListener {
        override fun onOutput(line: String?) {
            LOG.info(line)
        }
    }

    private val logListeners = mutableListOf<ProcessOutputListener>(
        logListener
    )

    fun addLogListener(listener: ProcessOutputListener) {
        logListeners.add(listener)
    }

    fun removeLogListener(listener: ProcessOutputListener) {
        logListeners.remove(listener)
    }

    fun startProcess(commands: List<String>, directory: String): Process? {
        val directoryFile = File(directory)
        Files.createDirectories(directoryFile.toPath())
        val processBuilder = ProcessBuilder(commands)
        processBuilder.directory(directoryFile)
        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        return process
    }

    private fun writeReaderToLog(reader: BufferedReader, listeners: List<ProcessOutputListener> = logListeners) {
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            listeners.forEach { listener ->
                listener.onOutput(line)
            }
        }
    }

    fun wait(process: Process) {
        process.waitFor()
    }

    fun waitAndPrintOutput(process: Process, listeners: List<ProcessOutputListener> = logListeners) {
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val errorReader = BufferedReader(InputStreamReader(process.errorStream))
        Thread {
            writeReaderToLog(reader, listeners)
        }.start()
        Thread {
            writeReaderToLog(errorReader, listeners)
        }.start()
        wait(process)
    }

    fun stopProcess(process: Process) {
        LOG.info("Stopping process - ${process.pid()}")
        process.destroy()
        process.waitFor()
        LOG.info("Process stopped - ${process.pid()}")
    }

    fun getOutput(process: Process): String {
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            output.append(line).append("\n")
        }
        return output.toString()
    }
}