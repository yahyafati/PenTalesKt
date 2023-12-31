package org.pentales.pentalesrest.components

import jakarta.annotation.*
import org.slf4j.*
import org.springframework.core.io.*
import org.springframework.jdbc.core.*
import org.springframework.stereotype.*
import org.springframework.util.*
import java.io.*
import java.nio.charset.*

@Component
class DatabaseInitializer(private val jdbcTemplate: JdbcTemplate) {

    companion object {

        private val LOG = LoggerFactory.getLogger(DatabaseInitializer::class.java)
    }

    init {
        LOG.info("DatabaseInitializer initialized")
    }

    @PostConstruct
    fun initializeDatabase() {
        LOG.info("Initializing database")
        executeScript("scripts/sql/activity_view.sql")
        LOG.info("Database initialized")
    }

    private fun executeScript(scriptPath: String) {
        try {
            LOG.info("Executing script: $scriptPath")
            val resource = ClassPathResource(scriptPath)
            val sqlScript = StreamUtils.copyToString(resource.inputStream, StandardCharsets.UTF_8)
            jdbcTemplate.execute(sqlScript)
        } catch (e: IOException) {
            LOG.error("Failed to execute script: $scriptPath - ${e.message}")
            e.printStackTrace()
        }
    }
}
