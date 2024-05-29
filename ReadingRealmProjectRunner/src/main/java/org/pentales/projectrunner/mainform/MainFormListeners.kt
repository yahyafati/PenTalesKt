package org.pentales.projectrunner.mainform

import org.pentales.projectrunner.util.*
import java.awt.event.*
import java.util.logging.*
import javax.swing.*

class MainFormListeners private constructor() {

    companion object {

        const val APP_DIR_NAME = "__app"

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
    private val backendServices = listOf("postgres", "sentiment-analysis", "springboot-app")
    private val frontendServices = listOf("frontend")

    constructor(mainForm: MainForm) : this() {
        this.mainForm = mainForm
    }

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
        FileUtils.copyResourceToFile("/docker-compose.yml", "$APP_DIR_NAME/docker-compose.yml")
        FileUtils.copyResourceToFile("/backend.env", "$APP_DIR_NAME/.env")
        FileUtils.copyResourceToFile("/keys/firebase-key.json", "$APP_DIR_NAME/firebase-key.json")

        backendProcess = ProcessUtils.startProcess(listOf("docker-compose", "up") + backendServices, APP_DIR_NAME)
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
            JOptionPane.showMessageDialog(mainForm, "Backend stopped successfully")
        }.start()
    }

    private fun stopBackend() {
        LOG.info("Stopping backend")
        if (backendProcess == null) {
            LOG.info("Backend is not running")
            return
        }
        ProcessUtils.stopProcess(backendProcess!!)

        val process = ProcessUtils.startProcess(
            listOf("docker-compose", "down") + backendServices,
            APP_DIR_NAME
        )
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
            JOptionPane.showMessageDialog(mainForm, "Backend updated successfully")
        }.start()
    }

    private fun updateBackend() {
        LOG.info("Updating backend")
        val process = ProcessUtils.startProcess(
            listOf("docker-compose", "pull") + backendServices,
            APP_DIR_NAME
        )
        if (process == null) {
            LOG.severe("Could not update backend")
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
        FileUtils.copyResourceToFile("/frontend.env", "$APP_DIR_NAME/frontend.env")
        frontendProcess = ProcessUtils.startProcess(
            listOf("docker-compose", "up") + frontendServices,
            APP_DIR_NAME
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

        val process = ProcessUtils.startProcess(
            listOf("docker-compose", "down") + frontendServices,
            APP_DIR_NAME
        )
        if (process == null) {
            LOG.severe("Could not stop frontend")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
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
            listOf("docker-compose", "pull", "frontend"),
            APP_DIR_NAME
        )
        if (process == null) {
            LOG.severe("Could not update frontend")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
    }

    fun windowListener(): WindowListener {
        return object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                val command = listOf("docker-compose", "down") + backendServices + frontendServices
                mainForm.updateStatusLabel("Status: Stopping all services, please wait...")
                LOG.info("Stopping all services")
                val process = ProcessUtils.startProcess(command, APP_DIR_NAME)
                if (process == null) {
                    LOG.severe("Could not stop all services")
                    return
                }
                ProcessUtils.waitAndPrintOutput(process)
                mainForm.updateStatusLabel("Status: All services stopped")
                super.windowClosing(e)
            }
        }
    }

    fun clearBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.BackendStatus.CLEARING
            val result = JOptionPane.showConfirmDialog(
                mainForm,
                "Are you sure you want to clear the data?\n\nThis will delete all data from the database.",
                "Clear backend",
                JOptionPane.YES_NO_OPTION
            )
            if (result != JOptionPane.YES_OPTION) {
                mainForm.backendStatus = MainForm.BackendStatus.STOPPED
                return@Thread
            }
            clearBackend()
            mainForm.backendStatus = MainForm.BackendStatus.STOPPED
            JOptionPane.showMessageDialog(mainForm, "Backend cleared successfully")
        }.start()
    }

    private fun clearBackend() {
        LOG.info("Clearing backend")
        val process = ProcessUtils.startProcess(
            listOf("docker-compose", "down", "--volumes") + backendServices,
            APP_DIR_NAME
        )
        if (process == null) {
            LOG.severe("Could not clear backend")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
    }

    fun clearFrontendListener() {
        Thread {
            mainForm.frontendStatus = MainForm.FrontendStatus.CLEARING
            val result = JOptionPane.showConfirmDialog(
                mainForm,
                "Are you sure you want to clear the data?",
                "Clear frontend",
                JOptionPane.YES_NO_OPTION
            )
            if (result != JOptionPane.YES_OPTION) {
                mainForm.frontendStatus = MainForm.FrontendStatus.STOPPED
                return@Thread
            }
            clearFrontend()
            mainForm.frontendStatus = MainForm.FrontendStatus.STOPPED
            JOptionPane.showMessageDialog(mainForm, "Frontend cleared successfully")
        }.start()
    }

    private fun clearFrontend() {
        LOG.info("Clearing frontend")
        val process = ProcessUtils.startProcess(
            listOf("docker-compose", "down", "--volumes") + frontendServices,
            APP_DIR_NAME
        )
        if (process == null) {
            LOG.severe("Could not clear frontend")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
    }

    fun populateBackendListener() {
        Thread {
            mainForm.backendStatus = MainForm.BackendStatus.STARTING
            populateBackend()
            mainForm.backendStatus = MainForm.BackendStatus.STOPPED
            JOptionPane.showMessageDialog(mainForm, "Backend populated successfully")
        }.start()
    }

    private fun populateBackend() {
        LOG.info("Populating backend")
        val postgresProcess = ProcessUtils.startProcess(
            listOf("docker-compose", "up", "postgres"),
            APP_DIR_NAME
        )

        if (postgresProcess == null) {
            LOG.severe("Could not start postgres")
            return
        }

        var count = 0
        val maxCount = 10

        do {
            LOG.info("Waiting for postgres to start, sleeping for 10 seconds")
            Thread.sleep(10000)

            val process = ProcessUtils.startProcess(
                listOf("docker", "exec", "postgres-db", "pg_isready"),
                APP_DIR_NAME
            )

            if (process == null) {
                LOG.severe("Could not check if postgres is ready")
                ProcessUtils.stopProcess(postgresProcess)
                return
            }

            val output = ProcessUtils.getOutput(process).trim()
            LOG.info("Output: $output")

            if ("accepting connections" in output) {
                LOG.info("Postgres is ready")
                break
            }
            count++
        } while (count < maxCount)

        if (count == maxCount) {
            LOG.severe("Postgres did not start")
            ProcessUtils.stopProcess(postgresProcess)
            return
        }

        val sqlFilePath = "init.sql"
        val containerName = "postgres-db"

        val dbUser = "postgres"
        val dbName = "reading_realm"
        val pgPassword = "password"

        val containerIdProcess = ProcessUtils.startProcess(
            listOf("docker", "ps", "-f", "name=$containerName", "--format", "{{.ID}}"),
            APP_DIR_NAME
        )

        if (containerIdProcess == null) {
            LOG.severe("Could not get container ID")
            return
        }
        val containerId = ProcessUtils.getOutput(containerIdProcess).trim()
        LOG.info("Container ID: $containerId")

        val dropDbCmd = "DROP DATABASE IF EXISTS $dbName;"
        val createDbCmd = "CREATE DATABASE $dbName;"

        val commands = listOf(
            "Copying" to listOf(
                "docker",
                "cp",
                sqlFilePath,
                "$containerId:/tmp/init.sql"
            ),
            "Dropping database" to listOf(
                "docker",
                "exec",
                "-e",
                "PGPASSWORD=$pgPassword",
                containerId,
                "psql",
                "-U",
                dbUser,
                "-c",
                dropDbCmd
            ),
            "Creating database" to listOf(
                "docker",
                "exec",
                "-e",
                "PGPASSWORD=$pgPassword",
                containerId,
                "psql",
                "-U",
                dbUser,
                "-c",
                createDbCmd
            ),
            "Populating database" to listOf(
                "docker",
                "exec",
                "-e",
                "PGPASSWORD=$pgPassword",
                containerId,
                "psql",
                "-U",
                dbUser,
                "-d",
                dbName,
                "-f",
                "/tmp/init.sql"
            )
        )


        for ((label, command) in commands) {
            LOG.info("Running $label")
            val process = ProcessUtils.startProcess(command, APP_DIR_NAME)
            if (process == null) {
                LOG.severe("Could not $label")
                return
            }
            ProcessUtils.waitAndPrintOutput(process)
            LOG.info("$label completed")
        }

        ProcessUtils.stopProcess(postgresProcess)
    }

}