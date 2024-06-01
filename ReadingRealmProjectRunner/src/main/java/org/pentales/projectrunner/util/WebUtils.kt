package org.pentales.projectrunner.util

import java.awt.*
import java.net.*
import java.util.logging.*

object WebUtils {

    private val LOG = Logger.getLogger(WebUtils::class.java.name)

    fun getResponseCode(url: String): Int {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            LOG.info("Response code: ${connection.responseCode}")
            connection.responseCode
        } catch (e: Exception) {
            LOG.warning("Could not get response code: ${e.message}")
            -1
        }
    }

    fun openBrowser(url: String) {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (e: Exception) {
            LOG.warning("Could not open browser: ${e.message}")
        }
    }
}