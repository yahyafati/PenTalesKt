package org.pentales.projectrunner.mainform

import java.awt.*
import javax.swing.*

class MainForm : JFrame() {

    enum class BackendStatus {
        STARTED,
        STARTING,
        STOPPED,
        STOPPING,
        UPDATING
    }

    enum class FrontendStatus {
        STARTED,
        STARTING,
        STOPPED,
        STOPPING,
        UPDATING,
        INSTALLING,
        DOWNLOADING
    }

    private val startBackendButton = JButton("Start Backend")
    private val stopBackendButton = JButton("Stop Backend")
    private val updateBackendButton = JButton("Update Backend")

    private val downloadFrontendButton = JButton("Download From Git")
    private val installModulesButton = JButton("Install Modules")
    private val startFrontendButton = JButton("Start Frontend")
    private val stopFrontendButton = JButton("Stop Frontend")
    private val updateFrontendButton = JButton("Update Frontend")

    private val exitAllButton = JButton("Exit All")

    private val listeners = MainFormListeners.getInstance(this)

    var backendStatus = BackendStatus.STOPPED
        set(value) {
            field = value
            repaint()
        }
    var frontendStatus = FrontendStatus.STOPPED
        set(value) {
            field = value
            repaint()
        }

    init {
        title = "Main Form"
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(400, 200)
        setLocationRelativeTo(null)
        initUI()
        initListeners()
        pack()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)

        startBackendButton.isEnabled = backendStatus == BackendStatus.STOPPED
        stopBackendButton.isEnabled = backendStatus == BackendStatus.STARTED
        updateBackendButton.isEnabled = backendStatus == BackendStatus.STOPPED

        startFrontendButton.isEnabled = frontendStatus == FrontendStatus.STOPPED
        stopFrontendButton.isEnabled = frontendStatus == FrontendStatus.STARTED
        updateFrontendButton.isEnabled = frontendStatus == FrontendStatus.STOPPED
        installModulesButton.isEnabled = frontendStatus == FrontendStatus.STOPPED
        downloadFrontendButton.isEnabled = frontendStatus == FrontendStatus.STOPPED
    }

    private fun initListeners() {
        startBackendButton.addActionListener { listeners.startBackendListener() }
        stopBackendButton.addActionListener { listeners.stopBackendListener() }
        updateBackendButton.addActionListener { listeners.updateBackendListener() }

        downloadFrontendButton.addActionListener { listeners.downloadFrontendListener() }
        installModulesButton.addActionListener { listeners.installModulesListener() }
        startFrontendButton.addActionListener { listeners.startFrontendListener() }
        stopFrontendButton.addActionListener { listeners.stopFrontendListener() }
        updateFrontendButton.addActionListener { listeners.updateFrontendListener() }
//
//        exitAllButton.addActionListener { listeners.exitAll() }
    }

    private fun initUI() {
        val contentPanel = JPanel()
        contentPane = contentPanel
        contentPanel.layout = BorderLayout()
        contentPanel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        val boxPanel = JPanel()
        boxPanel.layout = BoxLayout(boxPanel, BoxLayout.Y_AXIS)
        boxPanel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        val backendPanel = JPanel()
        backendPanel.layout = BoxLayout(backendPanel, BoxLayout.Y_AXIS)
        val backendLabel = JLabel("Backend")
        backendPanel.add(backendLabel)
        backendPanel.add(startBackendButton)
        backendPanel.add(stopBackendButton)
        backendPanel.add(updateBackendButton)

        val frontendPanel = JPanel()
        frontendPanel.layout = BoxLayout(frontendPanel, BoxLayout.Y_AXIS)
        val frontendLabel = JLabel("Frontend")
        frontendPanel.add(frontendLabel)
        frontendPanel.add(downloadFrontendButton)
        frontendPanel.add(installModulesButton)
        frontendPanel.add(startFrontendButton)
        frontendPanel.add(stopFrontendButton)
        frontendPanel.add(updateFrontendButton)

        boxPanel.add(backendPanel)
        boxPanel.add(frontendPanel)

        add(boxPanel, BorderLayout.NORTH)
        add(exitAllButton, BorderLayout.SOUTH)

    }
}