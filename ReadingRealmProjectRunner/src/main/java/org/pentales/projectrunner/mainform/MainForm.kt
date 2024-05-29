package org.pentales.projectrunner.mainform

import java.awt.*
import javax.swing.*

class MainForm : JFrame() {

    enum class Status {
        STARTED,
        STARTING,
        STOPPED,
        STOPPING,
        UPDATING
    }

    protected val startBackendButton = JButton("Start Backend")
    protected val stopBackendButton = JButton("Stop Backend")
    protected val updateBackendButton = JButton("Update Backend")

    protected val startFrontendButton = JButton("Start Frontend")
    protected val stopFrontendButton = JButton("Stop Frontend")
    protected val updateFrontendButton = JButton("Update Frontend")

    protected val exitAllButton = JButton("Exit All")

    private val listeners = MainFormListeners.getInstance(this)

    var backendStatus = Status.STOPPED
        set(value) {
            field = value
            repaint()
        }
    var frontendStatus = Status.STOPPED
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

        startBackendButton.isEnabled = backendStatus == Status.STOPPED
        stopBackendButton.isEnabled = backendStatus == Status.STARTED
        updateBackendButton.isEnabled = backendStatus == Status.STOPPED

        startFrontendButton.isEnabled = frontendStatus == Status.STOPPED
        stopFrontendButton.isEnabled = frontendStatus == Status.STARTED
        updateFrontendButton.isEnabled = frontendStatus == Status.STOPPED
    }

    private fun initListeners() {
        startBackendButton.addActionListener { listeners.startBackendListener() }
        stopBackendButton.addActionListener { listeners.stopBackendListener() }
        updateBackendButton.addActionListener { listeners.updateBackendListener() }
//
//        startFrontendButton.addActionListener { listeners.startFrontend() }
//        stopFrontendButton.addActionListener { listeners.stopFrontend() }
//        updateFrontendButton.addActionListener { listeners.updateFrontend() }
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
        backendPanel.layout = BoxLayout(backendPanel, BoxLayout.X_AXIS)
        val backendLabel = JLabel("Backend")
        backendPanel.add(backendLabel)
        backendPanel.add(startBackendButton)
        backendPanel.add(stopBackendButton)
        backendPanel.add(updateBackendButton)

        val frontendPanel = JPanel()
        frontendPanel.layout = BoxLayout(frontendPanel, BoxLayout.X_AXIS)
        val frontendLabel = JLabel("Frontend")
        frontendPanel.add(frontendLabel)
        frontendPanel.add(startFrontendButton)
        frontendPanel.add(stopFrontendButton)
        frontendPanel.add(updateFrontendButton)

        boxPanel.add(backendPanel)
        boxPanel.add(frontendPanel)

        add(boxPanel, BorderLayout.NORTH)
        add(exitAllButton, BorderLayout.SOUTH)

    }
}