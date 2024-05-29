package org.pentales.projectrunner.mainform

import java.awt.*
import javax.swing.*

class MainForm : JFrame() {

    enum class BackendStatus {
        STARTED,
        STARTING,
        STOPPED,
        STOPPING,
        UPDATING,
        CLEARING,
    }

    enum class FrontendStatus {
        STARTED,
        STARTING,
        STOPPED,
        STOPPING,
        UPDATING,
        CLEARING,
    }

    private val startBackendButton = JButton("Start Backend")
    private val stopBackendButton = JButton("Stop Backend")
    private val populateBackendButton = JButton("Populate Backend")
    private val updateBackendButton = JButton("Update Backend")
    private val clearBackendButton = JButton("Clear Backend")

    private val startFrontendButton = JButton("Start Frontend")
    private val stopFrontendButton = JButton("Stop Frontend")
    private val updateFrontendButton = JButton("Update Frontend")
    private val clearFrontendButton = JButton("Clear Frontend")

    private val statusLabel = JLabel("")

    private val listeners = MainFormListeners.getInstance(this)

    var backendStatus = BackendStatus.STOPPED
        set(value) {
            field = value
            backendStatusLabel.text = value.name
            repaint()
        }
    var frontendStatus = FrontendStatus.STOPPED
        set(value) {
            field = value
            frontendStatusLabel.text = value.name
            repaint()
        }

    private val backendStatusLabel = JLabel(backendStatus.name)
    private val frontendStatusLabel = JLabel(frontendStatus.name)

    init {
        title = "Main Form"
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(400, 380)
        setLocationRelativeTo(null)
        initUI()
        initListeners()
        addWindowListener(listeners.windowListener())
        pack()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)

        startBackendButton.isEnabled = backendStatus == BackendStatus.STOPPED
        stopBackendButton.isEnabled = backendStatus == BackendStatus.STARTED
        updateBackendButton.isEnabled = backendStatus == BackendStatus.STOPPED
        clearBackendButton.isEnabled = backendStatus == BackendStatus.STOPPED
        populateBackendButton.isEnabled = backendStatus == BackendStatus.STOPPED

        startFrontendButton.isEnabled = frontendStatus == FrontendStatus.STOPPED
        stopFrontendButton.isEnabled = frontendStatus == FrontendStatus.STARTED
        updateFrontendButton.isEnabled = frontendStatus == FrontendStatus.STOPPED
        clearFrontendButton.isEnabled = frontendStatus == FrontendStatus.STOPPED
    }

    private fun initListeners() {
        startBackendButton.addActionListener { listeners.startBackendListener() }
        stopBackendButton.addActionListener { listeners.stopBackendListener() }
        updateBackendButton.addActionListener { listeners.updateBackendListener() }
        clearBackendButton.addActionListener { listeners.clearBackendListener() }
        populateBackendButton.addActionListener { listeners.populateBackendListener() }

        startFrontendButton.addActionListener { listeners.startFrontendListener() }
        stopFrontendButton.addActionListener { listeners.stopFrontendListener() }
        updateFrontendButton.addActionListener { listeners.updateFrontendListener() }
        clearFrontendButton.addActionListener { listeners.clearFrontendListener() }
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
        backendPanel.border = BorderFactory.createEmptyBorder(0, 0, 10, 0)

        val backendLabelPanel = JPanel()
        backendLabelPanel.layout = BoxLayout(backendLabelPanel, BoxLayout.X_AXIS)

        val backendLabel = JLabel("Backend - ")
        backendLabelPanel.add(backendLabel)
        backendLabelPanel.add(backendStatusLabel)

        backendPanel.add(backendLabelPanel)
        backendPanel.add(startBackendButton)
        backendPanel.add(populateBackendButton)
        backendPanel.add(stopBackendButton)
        backendPanel.add(updateBackendButton)
        backendPanel.add(clearBackendButton)

        val frontendPanel = JPanel()
        frontendPanel.layout = BoxLayout(frontendPanel, BoxLayout.Y_AXIS)
        frontendPanel.border = BorderFactory.createEmptyBorder(10, 0, 0, 0)

        val frontendLabelPanel = JPanel()
        frontendLabelPanel.layout = BoxLayout(frontendLabelPanel, BoxLayout.X_AXIS)
        val frontendLabel = JLabel("Frontend - ")
        frontendLabelPanel.add(frontendLabel)
        frontendLabelPanel.add(frontendStatusLabel)

        frontendPanel.add(frontendLabelPanel)
        frontendPanel.add(startFrontendButton)
        frontendPanel.add(stopFrontendButton)
        frontendPanel.add(updateFrontendButton)
        frontendPanel.add(clearFrontendButton)

        boxPanel.add(backendPanel)
        boxPanel.add(frontendPanel)

        add(boxPanel, BorderLayout.CENTER)

        val statusPanel = JPanel()
        statusPanel.layout = BoxLayout(statusPanel, BoxLayout.Y_AXIS)
        statusPanel.add(statusLabel)
        add(statusPanel, BorderLayout.SOUTH)
    }

    fun updateStatusLabel(status: String) {
        statusLabel.text = "Status: $status"
        statusLabel.repaint()
    }
}