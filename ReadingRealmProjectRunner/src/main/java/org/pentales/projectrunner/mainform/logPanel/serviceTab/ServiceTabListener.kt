package org.pentales.projectrunner.mainform.logPanel.serviceTab

import org.pentales.projectrunner.mainform.*
import org.pentales.projectrunner.util.*
import java.time.*
import java.util.logging.*

class ServiceTabListener(private val serviceTab: ServiceTab) {

    companion object {

        private val LOG = Logger.getLogger(ServiceTabListener::class.java.name)
    }

    private var logProcess: Process? = null

    fun startLoggingListener() {
        LOG.info("Starting logging for ${serviceTab.serviceName}")
        Thread {
            serviceTab.status = ServiceTab.Status.STARTING
            startLogging()
        }.start()
    }

    private fun startLogging() {
        logProcess = DockerHelper.startLogging(serviceTab.serviceName, MainFormListeners.APP_DIR_NAME, true)
        serviceTab.status = ServiceTab.Status.STARTED

        val currentTime = LocalDateTime.now()
        serviceTab.textArea.text = "Started logging at $currentTime\n\n"

        val listener = object : ProcessUtils.ProcessOutputListener {
            override fun onOutput(line: String?) {
                serviceTab.textArea.append("$line\n")
            }
        }

        ProcessUtils.waitAndPrintOutput(logProcess!!, listOf(listener))

        serviceTab.status = ServiceTab.Status.STOPPED
    }

    fun stopLoggingListener() {
        LOG.info("Stopping logging for ${serviceTab.serviceName}")
        Thread {
            serviceTab.status = ServiceTab.Status.STOPPING
            stopLogging()
        }.start()
    }

    private fun stopLogging() {
        logProcess?.destroy()
        serviceTab.status = ServiceTab.Status.STOPPED
    }
}