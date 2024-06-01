package org.pentales.projectrunner.util

import java.util.logging.*

object DockerHelper {

    private val LOG = Logger.getLogger(DockerHelper::class.java.name)

    val SERVICES = listOf("postgres", "springboot-app", "frontend", "sentiment-analysis", "pgadmin")

    fun startContainers(directory: String, containers: List<String> = listOf()): Process {
        val command = mutableListOf("docker-compose", "up")
        command.addAll(containers)
        val process = ProcessUtils.startProcess(command, directory)
        if (process == null) {
            LOG.severe("Could not run docker-compose up command")
            throw Exception("Could not run docker-compose up command")
        }
        return process
    }

    fun stopContainers(directory: String, containers: List<String> = listOf()): Process {
        val command = mutableListOf("docker-compose", "down")
        command.addAll(containers)
        val process = ProcessUtils.startProcess(command, directory)
        if (process == null) {
            LOG.severe("Could not run docker-compose down command")
            throw Exception("Could not run docker-compose down command")
        }
        return process
    }

    fun clearContainers(directory: String, containers: List<String> = listOf()): Process {
        val command = mutableListOf("docker-compose", "down", "--volumes")
        command.addAll(containers)
        val process = ProcessUtils.startProcess(command, directory)
        if (process == null) {
            LOG.severe("Could not run docker-compose down --volumes command")
            throw Exception("Could not run docker-compose down --volumes command")
        }
        return process
    }

    fun getContainerId(container: String, directory: String): String {
        val process = ProcessUtils.startProcess(
            listOf("docker", "ps", "-q", "-f", "name=$container"),
            directory
        )
        if (process == null) {
            LOG.severe("Could not run docker ps command")
            throw Exception("Could not run docker ps command")
        }
        return ProcessUtils.getOutput(process).trim()
    }

    fun checkIfContainerIsRunning(container: String, directory: String): Boolean {
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

    fun waitUntilContainerIsRunning(
        container: String,
        directory: String,
        maxCount: Int = 10,
        sleepTime: Long = 1000
    ): Boolean {
        return waitUntilContainersAreRunning(listOf(container), directory, maxCount, sleepTime)
    }

    fun waitUntilContainersAreRunning(
        containers: List<String>,
        directory: String,
        maxCount: Int = 10,
        sleepTime: Long = 10_000
    ): Boolean {
        var count = 0

        var mapped = containers.associateWith { container ->
            checkIfContainerIsRunning(container, directory)
        }

        while (mapped.containsValue(false) && count < maxCount) {
            LOG.info("Waiting for containers to start, sleeping for $sleepTime ms")
            Thread.sleep(sleepTime)
            count++

            mapped = containers.associateWith { container ->
                checkIfContainerIsRunning(container, directory)
            }
        }

        val notRunningContainers = mapped.filter { !it.value }.keys
        if (notRunningContainers.isNotEmpty()) {
            LOG.severe("Containers did not start: $notRunningContainers")
        }

        return notRunningContainers.isEmpty()
    }
}