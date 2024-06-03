package org.pentales.projectrunner.util

import java.io.*
import java.nio.file.*
import java.util.logging.*

object AppHelper {

    const val APP_DIR_NAME = "__app"

    private val LOG = Logger.getLogger(AppHelper::class.java.name)

    fun resetConfigurationFiles() {
        LOG.info("Resetting configuration files")
        LOG.info("Stopping containers")
        val process = DockerHelper.stopContainers(APP_DIR_NAME)
        process.waitFor()
        LOG.info("Containers stopped")
        LOG.info("Clearing containers")
        val process2 = DockerHelper.clearContainers(APP_DIR_NAME)
        process2.waitFor()
        LOG.info("Containers cleared")

        LOG.info("Deleting $APP_DIR_NAME directory")
        FileUtils.deleteDirectory(APP_DIR_NAME)
        LOG.info("$APP_DIR_NAME directory deleted")
        copyConfigurationFiles()
    }

    fun copyConfigurationFiles() {
        LOG.info("Copying configuration files")
        initFolders()

        FileUtils.copyResourceToFile("/docker-compose.yml", "${APP_DIR_NAME}/docker-compose.yml")
        FileUtils.copyResourceToFile("/backend.env", "${APP_DIR_NAME}/.env")
        FileUtils.copyResourceToFile("/frontend.env", "${APP_DIR_NAME}/frontend.env")
        FileUtils.copyResourceToFile("/keys/firebase-key.json", "${APP_DIR_NAME}/firebase-key.json")
        FileUtils.copyResourceToFile("/init.sql", "${APP_DIR_NAME}/init.sql")
        LOG.info("Configuration files copied")
    }

    private fun initFolders() {
        val folders = arrayOf(
            APP_DIR_NAME,
            "${APP_DIR_NAME}/keys"
        )
        for (folder in folders) {
            Files.createDirectories(File(folder).toPath())
        }
    }
}