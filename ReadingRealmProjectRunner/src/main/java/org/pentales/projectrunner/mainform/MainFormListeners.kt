package org.pentales.projectrunner.mainform

import org.pentales.projectrunner.util.*
import java.util.logging.*

class MainFormListeners private constructor() {

    private var backendProcess: Process? = null
    private var frontendProcess: Process? = null

    fun startBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.BackendStatus.STARTING
            startBackend()
            mainForm.backendStatus = MainForm.BackendStatus.STOPPED
        }.start()
    }

    private fun startBackend() {
        LOG.info("Starting backend")
        FileUtils.copyResourceToFile("/docker-compose.yml", "backend/docker-compose.yml")
        FileUtils.copyResourceToFile("/backend.env", "backend/.env")
        FileUtils.copyResourceToFile("/keys/firebase-key.json", "backend/firebase-key.json")

        //        Open a command prompt and run the command "docker-compose up"
        backendProcess = ProcessUtils.startProcess(listOf("docker-compose", "up"), "backend")
        if (backendProcess == null) {
            LOG.severe("Could not start backend")
            return
        }
        mainForm.backendStatus = MainForm.BackendStatus.STARTED
        ProcessUtils.waitAndPrintOutput(backendProcess!!)
    }

    fun stopBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.BackendStatus.STOPPING
            stopBackend()
            mainForm.backendStatus = MainForm.BackendStatus.STOPPED
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
            mainForm.backendStatus = MainForm.BackendStatus.UPDATING
            updateBackend()
            mainForm.backendStatus = MainForm.BackendStatus.STOPPED
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

    fun downloadFrontendListener() {
        Thread {
            mainForm.frontendStatus = MainForm.FrontendStatus.DOWNLOADING
            downloadFrontend()
            mainForm.frontendStatus = MainForm.FrontendStatus.STOPPED
        }.start()
    }

    private fun downloadFrontend() {
        LOG.info("Downloading frontend")
        FileUtils.deleteDirectory("frontend")
        val repo = "git@github.com:yahyafati/book-review-react.git"
        val process = ProcessUtils.startProcess(
            listOf("git", "clone", repo, "."),
            "frontend"
        )
        if (process == null) {
            LOG.severe("Could not download frontend")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
        LOG.info("Downloaded frontend")
    }

    fun installModulesListener() {
        Thread {
            mainForm.frontendStatus = MainForm.FrontendStatus.INSTALLING
            installModules()
            mainForm.frontendStatus = MainForm.FrontendStatus.STOPPED
        }.start()
    }

    private fun installModules() {
        LOG.info("Installing frontend modules")
        val process = ProcessUtils.startProcess(
            listOf("npm", "install"),
            "frontend"
        )
        if (process == null) {
            LOG.severe("Could not install frontend modules")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
    }

    fun startFrontendListener() {
        Thread {
            mainForm.frontendStatus = MainForm.FrontendStatus.STARTING
            startFrontend()
            mainForm.frontendStatus = MainForm.FrontendStatus.STOPPED
        }.start()
    }

    private fun startFrontend() {
        LOG.info("Starting frontend")
        FileUtils.copyResourceToFile("/frontend.env", "frontend/.env")
        frontendProcess = ProcessUtils.startProcess(
            listOf("npm", "run", "dev"),
            "frontend"
        )
        if (frontendProcess == null) {
            LOG.severe("Could not start frontend")
            return
        }
        mainForm.frontendStatus = MainForm.FrontendStatus.STARTED
        ProcessUtils.waitAndPrintOutput(frontendProcess!!)
    }

    fun stopFrontendListener() {
        Thread {
            mainForm.frontendStatus = MainForm.FrontendStatus.STOPPING
            stopFrontend()
            mainForm.frontendStatus = MainForm.FrontendStatus.STOPPED
        }.start()
    }

    private fun stopFrontend() {
        LOG.info("Stopping frontend")
        if (frontendProcess == null) {
            LOG.info("Frontend is not running")
            return
        }
        ProcessUtils.stopProcess(frontendProcess!!)
    }

    fun updateFrontendListener() {
        Thread {
            mainForm.frontendStatus = MainForm.FrontendStatus.UPDATING
            updateFrontend()
            mainForm.frontendStatus = MainForm.FrontendStatus.STOPPED
        }.start()
    }

    private fun updateFrontend() {
        LOG.info("Updating frontend")
        val process = ProcessUtils.startProcess(
            listOf("git", "pull"),
            "frontend"
        )
        if (process == null) {
            LOG.severe("Could not update frontend")
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