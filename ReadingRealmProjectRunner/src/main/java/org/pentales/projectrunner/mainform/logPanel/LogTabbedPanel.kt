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

        val allTab = ServiceTab("all", true)
        ProcessUtils.addLogListener(object : ProcessUtils.ProcessOutputListener {
            override fun onOutput(line: String?) {
                allTab.textArea.append(line + "\n")
            }
        })
        addTab("All Services", allTab)
        addTab("Database Service", ServiceTab("database"))
        addTab("Backend Service", ServiceTab("backend"))
        addTab("Frontend Service", ServiceTab("frontend"))
        addTab("Sentiment Analysis Service", ServiceTab("sentiment-analysis"))
    }

}