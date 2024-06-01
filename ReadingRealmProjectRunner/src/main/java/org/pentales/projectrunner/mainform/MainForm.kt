package org.pentales.projectrunner.mainform

import org.pentales.projectrunner.util.*
import java.awt.*
import java.util.*
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

    private val dockerStatusLabel = JLabel("")
    private val dockerComposeStatusLabel = JLabel("")

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
        minimumSize = Dimension(300, 0)
        initUI()
        initListeners()
        addWindowListener(listeners.windowListener())
        pack()
        setLocationRelativeTo(null)
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        val DARK_GREEN = Color(0, 153, 0)
        val DARK_RED = Color(153, 0, 0)

        startButton.isEnabled = status == Status.STOPPED
        stopButton.isEnabled = status in listOf(
            Status.STARTED,
            Status.STARTING,
            Status.STOPPED
        )
        updateContainerButton.isEnabled = status == Status.STOPPED
        clearDataButton.isEnabled = status == Status.STOPPED
        populateDatabaseButton.isEnabled = status == Status.STOPPED

        servicesStatusLabel.text =
            status.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        servicesStatusLabel.foreground = when (status) {
            Status.STARTED -> DARK_GREEN
            Status.STARTING -> Color.BLUE
            Status.STOPPED -> DARK_RED
            Status.STOPPING -> Color.BLUE
            Status.UPDATING -> Color.BLUE
            Status.CLEARING -> Color.BLUE
        }

        val dockerStatus = DockerHelper.isDockerInstalled()
        dockerStatusLabel.text = if (dockerStatus) "Installed" else "Not Installed"
        dockerStatusLabel.foreground = if (dockerStatus) DARK_GREEN else DARK_RED

        val dockerComposeStatus = DockerHelper.isDockerComposeInstalled()
        dockerComposeStatusLabel.text = if (dockerComposeStatus) "Installed" else "Not Installed"
        dockerComposeStatusLabel.foreground = if (dockerComposeStatus) DARK_GREEN else DARK_RED

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

        val installedPanel = JPanel()
        installedPanel.layout = BoxLayout(installedPanel, BoxLayout.Y_AXIS)
        installedPanel.border = BorderFactory.createEmptyBorder(0, 0, 10, 0)

        val dockerPanel = JPanel()
        val font = this.dockerStatusLabel.font
        dockerPanel.layout = BoxLayout(dockerPanel, BoxLayout.X_AXIS)
        val dockerStatusLabel = JLabel("Docker Status: ")
        dockerPanel.add(dockerStatusLabel)

        this.dockerStatusLabel.font = font.deriveFont(font.style or Font.BOLD)
        dockerPanel.add(this.dockerStatusLabel)

        val dockerComposePanel = JPanel()
        dockerComposePanel.layout = BoxLayout(dockerComposePanel, BoxLayout.X_AXIS)

        val dockerComposeStatusLabel = JLabel("Docker Compose Status: ")
        dockerComposePanel.add(dockerComposeStatusLabel)
        this.dockerComposeStatusLabel.font = font.deriveFont(font.style or Font.BOLD)
        dockerComposePanel.add(this.dockerComposeStatusLabel)

        val servicesLabelPanel = JPanel()
        servicesLabelPanel.layout = BoxLayout(servicesLabelPanel, BoxLayout.X_AXIS)
        val servicesLabel = JLabel("Services - ")
        servicesLabelPanel.add(servicesLabel)
        servicesStatusLabel.font = font.deriveFont(font.style or Font.BOLD)
        servicesLabelPanel.add(servicesStatusLabel)

        installedPanel.add(dockerPanel)
        installedPanel.add(dockerComposePanel)
        installedPanel.add(Box.createVerticalStrut(10))
        installedPanel.add(servicesLabelPanel)


        contentPanel.add(installedPanel, BorderLayout.NORTH)

        val servicesPanel = JPanel()
        servicesPanel.layout = BoxLayout(servicesPanel, BoxLayout.Y_AXIS)
        servicesPanel.border = BorderFactory.createEmptyBorder(0, 0, 10, 0)



        servicesPanel.add(startButton)
        servicesPanel.add(populateDatabaseButton)
        servicesPanel.add(stopButton)
        servicesPanel.add(updateContainerButton)
        servicesPanel.add(clearDataButton)


        add(servicesPanel, BorderLayout.CENTER)

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