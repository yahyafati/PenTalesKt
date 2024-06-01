package org.pentales.projectrunner.mainform

import java.awt.*
import javax.swing.*

class MainForm : JFrame() {

    enum class Status {
        STARTED,
        STARTING,
        STOPPED,
        STOPPING,
        UPDATING,
        CLEARING,
    }

    private val startButton = JButton("Start")
    private val stopButton = JButton("Stop")
    private val populateDatabaseButton = JButton("Populate Database")
    private val updateContainerButton = JButton("Update Container")
    private val clearDataButton = JButton("Clear Data")

    private val statusLabel = JLabel("")

    private val listeners = MainFormListeners.getInstance(this)

    var status = Status.STOPPED
        set(value) {
            field = value
            servicesStatusLabel.text = value.name
            repaint()
        }

    private val servicesStatusLabel = JLabel(status.name)

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

        startButton.isEnabled = status == Status.STOPPED
        stopButton.isEnabled = status == Status.STARTED
        updateContainerButton.isEnabled = status == Status.STOPPED
        clearDataButton.isEnabled = status == Status.STOPPED
        populateDatabaseButton.isEnabled = status == Status.STOPPED

    }

    private fun initListeners() {
        startButton.addActionListener { listeners.startListener() }
        stopButton.addActionListener { listeners.stopListener() }
        updateContainerButton.addActionListener { listeners.updateContainerListener() }
        clearDataButton.addActionListener { listeners.clearDataListener() }
        populateDatabaseButton.addActionListener { listeners.populateDatabaseListener() }

    }

    private fun initUI() {
        val contentPanel = JPanel()
        contentPane = contentPanel
        contentPanel.layout = BorderLayout()
        contentPanel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        val boxPanel = JPanel()
        boxPanel.layout = BoxLayout(boxPanel, BoxLayout.Y_AXIS)
        boxPanel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        val servicesPanel = JPanel()
        servicesPanel.layout = BoxLayout(servicesPanel, BoxLayout.Y_AXIS)
        servicesPanel.border = BorderFactory.createEmptyBorder(0, 0, 10, 0)

        val servicesLabelPanel = JPanel()
        servicesLabelPanel.layout = BoxLayout(servicesLabelPanel, BoxLayout.X_AXIS)

        val servicesLabel = JLabel("Services - ")
        servicesLabelPanel.add(servicesLabel)
        servicesLabelPanel.add(servicesStatusLabel)

        servicesPanel.add(servicesLabelPanel)
        servicesPanel.add(startButton)
        servicesPanel.add(populateDatabaseButton)
        servicesPanel.add(stopButton)
        servicesPanel.add(updateContainerButton)
        servicesPanel.add(clearDataButton)


        boxPanel.add(servicesPanel)

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