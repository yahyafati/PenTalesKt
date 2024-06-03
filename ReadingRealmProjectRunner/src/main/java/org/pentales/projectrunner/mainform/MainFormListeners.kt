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
    private var process: Process? = null

    constructor(mainForm: MainForm) : this() {
        this.mainForm = mainForm
    }

    fun startListener() {
        Thread {
            mainForm.status = MainForm.Status.STARTING
            startContainer()
        }.start()
    }

    private fun startContainer() {
        stopContainer()
        LOG.info("Starting container")

        process = DockerHelper.startContainers(APP_DIR_NAME)

        Thread {
            ServicesUtil.waitUntilBackendIsRunningThreadControl.shouldStop = false
            val wait = ServicesUtil.waitUntilBackendIsRunning()
            if (!wait) {
                return@Thread
            }
            LOG.info("Spring Boot API is running")
            mainForm.status = MainForm.Status.STARTED

            LOG.info("Opening browser at http://localhost:5173/")
            WebUtils.openBrowser("http://localhost:5173/")
            LOG.info("Browser opened")
        }.start()

        ProcessUtils.waitAndPrintOutput(process!!)

    }

    fun stopListener() {
        Thread {
            mainForm.status = MainForm.Status.STOPPING
            stopContainer()
            mainForm.status = MainForm.Status.STOPPED
        }.start()
    }

    private fun stopContainer() {
        LOG.info("Stopping container")
        ServicesUtil.waitUntilBackendIsRunningThreadControl.shouldStop = true

        val process = DockerHelper.stopContainers(APP_DIR_NAME)
        ProcessUtils.waitAndPrintOutput(process)

        if (this.process != null) {
            ProcessUtils.stopProcess(this.process!!)
        }
    }

    fun updateContainerListener() {
        Thread {
            mainForm.status = MainForm.Status.UPDATING
            updateContainer()
            mainForm.status = MainForm.Status.STOPPED
            JOptionPane.showMessageDialog(mainForm, "Backend updated successfully")
        }.start()
    }

    private fun updateContainer() {
        stopContainer()
        LOG.info("Updating container")
        val process = ProcessUtils.startProcess(
            listOf("docker-compose", "pull"),
            APP_DIR_NAME
        )
        if (process == null) {
            LOG.severe("Could not update container")
            return
        }
        ProcessUtils.waitAndPrintOutput(process)
    }

    fun clearDataListener() {
        val result = JOptionPane.showConfirmDialog(
            mainForm,
            "Are you sure you want to clear the data?\n\nThis will delete all data from the database.",
            "Clear backend",
            JOptionPane.YES_NO_OPTION
        )
        if (result != JOptionPane.YES_OPTION) {
            mainForm.status = MainForm.Status.STOPPED
            return
        }
        Thread {
            mainForm.status = MainForm.Status.CLEARING
            clearData()
            mainForm.status = MainForm.Status.STOPPED
            JOptionPane.showMessageDialog(mainForm, "Backend cleared successfully")
        }.start()
    }

    private fun clearData() {
        stopContainer()
        LOG.info("Clearing backend")
        val process = DockerHelper.clearContainers(APP_DIR_NAME)
        ProcessUtils.waitAndPrintOutput(process)
    }

    fun populateDatabaseListener() {
        Thread {
            mainForm.status = MainForm.Status.STARTING
            populateDatabase()
            mainForm.status = MainForm.Status.STOPPED
            JOptionPane.showMessageDialog(mainForm, "Backend populated successfully")
        }.start()
    }

    private fun populateDatabase() {
        stopContainer()
        LOG.info("Populating backend")
        val postgresProcess = DockerHelper.startContainers(APP_DIR_NAME, listOf(DockerHelper.SERVICES.POSTGRES))

        val wait = DockerHelper.waitUntilContainerIsRunning(DockerHelper.SERVICES.POSTGRES, APP_DIR_NAME)

        if (!wait) {
            LOG.severe("Could not start ${DockerHelper.SERVICES.POSTGRES.container} container")
            return
        }

        val dbUser = "postgres"
        val dbName = "reading_realm"
        val pgPassword = "password"

        val containerId = DockerHelper.getContainerId(DockerHelper.SERVICES.POSTGRES, APP_DIR_NAME)
        LOG.info("Container ID: $containerId")

        val dropDbCmd = "DROP DATABASE IF EXISTS $dbName;"
        val createDbCmd = "CREATE DATABASE $dbName;"

        val dockerPSQLExec = "docker exec -e PGPASSWORD=$pgPassword $containerId psql -U $dbUser".split(" ")

        val commands = listOf(
            "Dropping database" to
                    dockerPSQLExec + listOf("-c", dropDbCmd),
            "Creating database" to
                    dockerPSQLExec + listOf("-c", createDbCmd),
            "Populating database" to
                    dockerPSQLExec + listOf("-d", dbName, "-f", "/tmp/init.sql"),
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

    fun windowListener(): WindowListener {
        return object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                mainForm.updateStatusLabel("Status: Stopping all services, please wait...")
                LOG.info("Stopping all services")
                val process = DockerHelper.stopContainers(APP_DIR_NAME)
                ProcessUtils.waitAndPrintOutput(process)
                mainForm.updateStatusLabel("Status: All services stopped")
                super.windowClosing(e)
            }
        }
    }

    fun resetListener() {
        val result = JOptionPane.showConfirmDialog(
            mainForm,
            "Are you sure you want to reset the configuration files?\n\n" +
                    "This will delete all data from the database and any changes " +
                    "made to the configuration files.",
            "Reset configuration files",
            JOptionPane.YES_NO_OPTION
        )

        if (result != JOptionPane.YES_OPTION) {
            return
        }
        Thread {
            mainForm.status = MainForm.Status.STOPPING
            reset()
            mainForm.status = MainForm.Status.STOPPED
            JOptionPane.showMessageDialog(mainForm, "Configuration files reset successfully")
        }.start()
    }

    private fun reset() {
        stopContainer()
        AppHelper.resetConfigurationFiles()
    }

}