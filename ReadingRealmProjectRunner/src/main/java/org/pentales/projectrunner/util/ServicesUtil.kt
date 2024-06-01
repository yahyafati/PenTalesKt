package org.pentales.projectrunner.util

import java.util.logging.*

object ServicesUtil {

    private val LOG = Logger.getLogger(ServicesUtil::class.java.name)

    fun isBackendRunning(): Boolean {
        val responseCode = WebUtils.getResponseCode("http://localhost:8080/actuator/health")
        return responseCode == 200
    }

    fun isFrontendRunning(): Boolean {
        val responseCode = WebUtils.getResponseCode("http://localhost:5173")
        return responseCode == 200
    }

    fun waitUntilBackendIsRunning(maxCount: Int = 30, sleepDuration: Long = 10_000L): Boolean {
        var count = 0
        while (count < maxCount) {
            if (isBackendRunning()) {
                return true
            }
            LOG.info("Waiting for backend to start, sleeping for $sleepDuration ms (attempt $count / $maxCount)")
            Thread.sleep(sleepDuration)
            count++
        }
        return false
    }
}