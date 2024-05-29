package org.pentales.projectrunner.mainform

import org.pentales.projectrunner.util.*
import java.util.logging.*

class MainFormListeners private constructor() {

    private var backendProcess: Process? = null

    fun startBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.Status.STARTING
            startBackend()
            mainForm.backendStatus = MainForm.Status.STOPPED
        }.start()
    }

    private fun startBackend() {
        LOG.info("Starting backend")
        FileUtils.copyResourceToFile("/docker-compose.yml", "backend/docker-compose.yml")
        FileUtils.copyResourceToFile("/.env", "backend/.env")
        FileUtils.copyResourceToFile("/keys/firebase-key.json", "backend/firebase-key.json")

        //        Open a command prompt and run the command "docker-compose up"
        backendProcess = ProcessUtils.startProcess(listOf("docker-compose", "up"), "backend")
        if (backendProcess == null) {
            LOG.severe("Could not start backend")
            return
        }
        mainForm.backendStatus = MainForm.Status.STARTED
        ProcessUtils.waitAndPrintOutput(backendProcess!!)
    }

    fun stopBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.Status.STOPPING
            stopBackend()
            mainForm.backendStatus = MainForm.Status.STOPPED
        }.start()
    }

    private fun stopBackend() {
        LOG.info("Stopping backend")
        if (backendProcess == null) {
            LOG.info("Backend is not running")
            return
        }
        ProcessUtils.stopProcess(backendProcess!!)

        val process = ProcessUtils.startProcess(listOf("docker-compose", "down"), "backend")
        if (process == null) {
            LOG.severe("Could not stop backend")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
    }

    fun updateBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.Status.UPDATING
            updateBackend()
            mainForm.backendStatus = MainForm.Status.STOPPED
        }.start()
    }

    private fun updateBackend() {
        LOG.info("Updating backend")
        val process = ProcessUtils.startProcess(listOf("docker-compose", "pull"), "backend")
        if (process == null) {
            LOG.severe("Could not update backend")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
    }

    companion object {

        private var INSTANCE: MainFormListeners? = null

        private val LOG = Logger.getLogger(MainFormListeners::class.java.name)

        fun getInstance(mainForm: MainForm): MainFormListeners {
            if (INSTANCE == null) {
                LOG.info("Creating new instance of MainFormListeners")
                INSTANCE = MainFormListeners(mainForm)
            }
            return INSTANCE!!
        }
    }

    private lateinit var mainForm: MainForm

    constructor(mainForm: MainForm) : this() {
        this.mainForm = mainForm
    }

}