package org.pentales.projectrunner.mainform.logPanel

import org.pentales.projectrunner.mainform.logPanel.serviceTab.*
import org.pentales.projectrunner.util.*
import java.awt.*
import javax.swing.*

class LogTabbedPanel private constructor() : JTabbedPane() {

    companion object {

        private var instance: LogTabbedPanel? = null

        fun getInstance(): LogTabbedPanel {
            if (instance == null) {
                instance = LogTabbedPanel()
            }
            return instance!!
        }
    }

    init {
        minimumSize = Dimension(300, 300)

        val allTab = ServiceTab(DockerHelper.SERVICES.ALL, true)
        ProcessUtils.addLogListener(object : ProcessUtils.ProcessOutputListener {
            override fun onOutput(line: String?) {
                allTab.textArea.append(line + "\n")
            }
        })
        addTab("All Services", allTab)
        addTab("Database Service", ServiceTab(DockerHelper.SERVICES.POSTGRES))
        addTab("Backend Service", ServiceTab(DockerHelper.SERVICES.BACKEND))
        addTab("Frontend Service", ServiceTab(DockerHelper.SERVICES.FRONTEND))
        addTab("Sentiment Analysis Service", ServiceTab(DockerHelper.SERVICES.SENTIMENT_ANALYSIS))
        addTab("pgAdmin", ServiceTab(DockerHelper.SERVICES.PGADMIN))
    }

}