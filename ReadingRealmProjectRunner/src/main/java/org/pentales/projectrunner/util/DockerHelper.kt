package org.pentales.projectrunner.util

import org.pentales.projectrunner.mainform.*
import java.util.logging.*

object DockerHelper {

    private val LOG = Logger.getLogger(DockerHelper::class.java.name)

    enum class SERVICES(val service: String, val container: String) {
        ALL("", ""),
        POSTGRES("database", "database"),
        BACKEND("backend", "backend"),
        FRONTEND("frontend", "frontend"),
        SENTIMENT_ANALYSIS("sentiment-analysis", "sentiment-analysis"),
        PGADMIN("pgadmin", "pgadmin")
    }

    fun isDockerInstalled(): Boolean {
        val process = ProcessUtils.startProcess(listOf("docker", "--version"), MainFormListeners.APP_DIR_NAME)
        return process != null
    }

    fun isDockerComposeInstalled(): Boolean {
        val process = ProcessUtils.startProcess(listOf("docker-compose", "--version"), MainFormListeners.APP_DIR_NAME)
        return process != null
    }

    fun startContainers(directory: String, containers: List<SERVICES> = listOf(), detached: Boolean = false): Process {
        val command = mutableListOf("docker-compose", "up")
        command.addAll(containers.map { it.service })
        if (detached) {
            command.add("-d")
        }

        val process = ProcessUtils.startProcess(command, directory)
        if (process == null) {
            LOG.severe("Could not run docker-compose up command")
            throw Exception("Could not run docker-compose up command")
        }
        return process
    }

    fun stopContainers(directory: String, containers: List<SERVICES> = listOf()): Process {
        val command = mutableListOf("docker-compose", "down")
        command.addAll(containers.map { it.service })
        val process = ProcessUtils.startProcess(command, directory)
        if (process == null) {
            LOG.severe("Could not run docker-compose down command")
            throw Exception("Could not run docker-compose down command")
        }
        return process
    }

    fun clearContainers(directory: String, containers: List<SERVICES> = listOf()): Process {
        val command = mutableListOf("docker-compose", "down", "--volumes")
        command.addAll(containers.map { it.service })
        val process = ProcessUtils.startProcess(command, directory)
        if (process == null) {
            LOG.severe("Could not run docker-compose down --volumes command")
            throw Exception("Could not run docker-compose down --volumes command")
        }
        return process
    }

    fun startLogging(container: SERVICES, directory: String, follow: Boolean = true): Process {
        val command = mutableListOf("docker", "logs")
        command.add(container.container)
        if (follow) {
            command.add("-f")
        }
        val process = ProcessUtils.startProcess(command, directory)
        if (process == null) {
            LOG.severe("Could not run docker logs command")
            throw Exception("Could not run docker logs command")
        }
        return process
    }

    fun getContainerId(container: SERVICES, directory: String): String {
        val process = ProcessUtils.startProcess(
            listOf("docker", "ps", "-q", "-f", "name=${container.container}"),
            directory
        )
        if (process == null) {
            LOG.severe("Could not run docker ps command")
            throw Exception("Could not run docker ps command")
        }
        return ProcessUtils.getOutput(process).trim()
    }

    private fun checkIfContainerIsRunning(container: SERVICES, directory: String): Boolean {
        val containerId = getContainerId(container, directory)

        if (containerId.isEmpty()) {
            return false
        }

        val process = ProcessUtils.startProcess(
            listOf("docker", "inspect", "-f", "{{.State.Running}}", containerId),
            directory
        )

        if (process == null) {
            LOG.severe("Could not check if container is running")
            throw Exception("Could not check if container is running")
        }

        val output = ProcessUtils.getOutput(process).trim()

        return output == "true"
    }

    fun checkIfPostgresIsAcceptingConnections(container: SERVICES, directory: String): Boolean {
        val containerId = getContainerId(container, directory)

        if (containerId.isEmpty()) {
            return false
        }

        val process = ProcessUtils.startProcess(
            listOf("docker", "exec", containerId, "pg_isready"),
            directory
        )

        if (process == null) {
            LOG.severe("Could not check if postgres is accepting connections")
            throw Exception("Could not check if postgres is accepting connections")
        }

        val output = ProcessUtils.getOutput(process).trim()

        return "accepting connections" in output
    }

    fun waitUntilContainerIsRunning(
        container: SERVICES,
        directory: String,
        maxCount: Int = 10,
        sleepTime: Long = 1000,
        checkingFunction: (SERVICES, String) -> Boolean = ::checkIfContainerIsRunning
    ): Boolean {
        return waitUntilContainersAreRunning(listOf(container), directory, maxCount, sleepTime, checkingFunction)
    }

    fun waitUntilContainersAreRunning(
        containers: List<SERVICES>,
        directory: String,
        maxCount: Int = 10,
        sleepTime: Long = 10_000,
        checkingFunction: (SERVICES, String) -> Boolean = ::checkIfContainerIsRunning
    ): Boolean {
        var count = 0

        var mapped = containers.associateWith { container ->
            checkingFunction(container, directory)
        }

        while (mapped.containsValue(false) && count < maxCount) {
            LOG.info("Waiting for containers to start, sleeping for $sleepTime ms")
            Thread.sleep(sleepTime)
            count++

            mapped = containers.associateWith { container ->
                checkingFunction(container, directory)
            }
        }

        val notRunningContainers = mapped.filter { !it.value }.keys
        if (notRunningContainers.isNotEmpty()) {
            LOG.severe("Containers did not start: $notRunningContainers")
        }

        return notRunningContainers.isEmpty()
    }
}