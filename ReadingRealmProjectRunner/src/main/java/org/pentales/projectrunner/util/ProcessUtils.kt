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

    fun waitAndPrintOutput(process: Process) {
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val errorReader = BufferedReader(InputStreamReader(process.errorStream))
        Thread {
            writeReaderToLog(reader)
        }.start()
        Thread {
            writeReaderToLog(errorReader)
        }.start()
        process.waitFor()
    }

    fun stopProcess(process: Process) {
        process.destroy()
    }
}