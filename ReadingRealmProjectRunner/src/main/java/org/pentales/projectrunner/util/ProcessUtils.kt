package org.pentales.projectrunner.util

import org.pentales.projectrunner.mainform.*
import java.io.*
import java.util.logging.*

object ProcessUtils {

    val LOG = Logger.getLogger(MainFormListeners::class.java.name)

    fun startProcess(commands: List<String>, directory: String): Process? {
        val processBuilder = ProcessBuilder(commands)
        processBuilder.directory(File(directory))
        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        return process
    }

    fun waitAndPrintOutput(process: Process) {
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            LOG.info(line)
        }
        process.waitFor()
    }

    fun stopProcess(process: Process) {
        process.destroy()
    }
}