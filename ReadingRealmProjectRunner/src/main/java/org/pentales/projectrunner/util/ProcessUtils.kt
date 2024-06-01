package org.pentales.projectrunner.util

import org.pentales.projectrunner.mainform.*
import java.io.*
import java.nio.file.*
import java.util.logging.*

object ProcessUtils {

    val LOG = Logger.getLogger(MainFormListeners::class.java.name)

    fun startProcess(commands: List<String>, directory: String): Process? {
        val directoryFile = File(directory)
        Files.createDirectories(directoryFile.toPath())
        val processBuilder = ProcessBuilder(commands)
        processBuilder.directory(directoryFile)
        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        return process
    }

    private fun writeReaderToLog(reader: BufferedReader) {
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            LOG.info(line)
        }
    }

    fun wait(process: Process) {
        process.waitFor()
    }

    fun waitAndPrintOutput(process: Process) {
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val errorReader = BufferedReader(InputStreamReader(process.errorStream))
        Thread {
            writeReaderToLog(reader)
        }.start()
        Thread {
            writeReaderToLog(errorReader)
        }.start()
        wait(process)
    }

    fun stopProcess(process: Process) {
        process.destroy()
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