package org.pentales.projectrunner.mainform

import java.io.*
import java.nio.file.*
import java.util.logging.*

class MainFormListeners private constructor() {

    var backendProcess: Process? = null

    fun startBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.Status.STARTING
            startBackend()
            mainForm.backendStatus = MainForm.Status.STOPPED
        }.start()
    }

    private fun startBackend() {
        LOG.info("Starting backend")
        Files.createDirectories(File("backend").toPath())
        val dockerComposeFile = File("backend/docker-compose.yml")
        if (!dockerComposeFile.exists()) {
            LOG.info("Copying docker-compose.yml file to backend directory")
            val inputStream = javaClass.getResourceAsStream("/docker-compose.yml")
            val outputStream = FileOutputStream("backend/docker-compose.yml")
            inputStream.use { input ->
                outputStream.use { output ->
                    input?.copyTo(output) ?: {
                        LOG.severe("Could not copy docker-compose.yml file to backend directory")
                        throw IOException("Could not copy docker-compose.yml file to backend directory")
                    }
                }
            }
        }

        val environmentFile = File("backend/.env")
        if (!environmentFile.exists()) {
            LOG.info("Copying .env file to backend directory")
            val inputStream = javaClass.getResourceAsStream("/.env")
            val outputStream = FileOutputStream("backend/.env")
            inputStream.use { input ->
                outputStream.use { output ->
                    input?.copyTo(output) ?: {
                        LOG.severe("Could not copy .env file to backend directory")
                        throw IOException("Could not copy .env file to backend directory")
                    }
                }
            }
        }

        val firebaseKeyFile = File("backend/firebase-key.json")
        if (!firebaseKeyFile.exists()) {
            LOG.info("Copying firebase-key.json file to backend directory")
            val inputStream = javaClass.getResourceAsStream("/keys/firebase-key.json")
            val outputStream = FileOutputStream("backend/firebase-key.json")
            inputStream.use { input ->
                outputStream.use { output ->
                    input?.copyTo(output) ?: {
                        LOG.severe("Could not copy firebase-key.json file to backend directory")
                        throw IOException("Could not copy firebase-key.json file to backend directory")
                    }
                }
            }
        }

        //        Open a command prompt and run the command "docker-compose up"
        val processBuilder = ProcessBuilder("docker-compose", "up")
        processBuilder.directory(File("backend"))
        processBuilder.redirectErrorStream(true)
        backendProcess = processBuilder.start()
        mainForm.backendStatus = MainForm.Status.STARTED
        if (backendProcess == null) {
            LOG.severe("Could not start backend")
            return
        }
        val reader = BufferedReader(InputStreamReader(backendProcess!!.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            LOG.info(line)
        }
        backendProcess!!.waitFor()
        backendProcess = null
    }

    fun stopBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.Status.STOPPING
            stopBackend()
        }.start()
    }

    private fun stopBackend() {
        LOG.info("Stopping backend")
        if (backendProcess == null) {
            LOG.info("Backend is not running")
            return
        }
        backendProcess!!.destroy()

//        Stop Docker containers
        val processBuilder = ProcessBuilder("docker-compose", "down")
        processBuilder.directory(File("backend"))
        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        if (process == null) {
            LOG.severe("Could not stop backend")
            return
        }
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            LOG.info(line)
        }
        process.waitFor()
        mainForm.backendStatus = MainForm.Status.STOPPED
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
        val processBuilder = ProcessBuilder("docker-compose", "pull")
        processBuilder.directory(File("backend"))
        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        if (process == null) {
            LOG.severe("Could not update backend")
            return
        }
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            LOG.info(line)
        }
        process.waitFor()
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