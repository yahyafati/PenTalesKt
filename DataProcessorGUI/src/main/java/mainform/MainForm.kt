package mainform

import settings.*
import java.awt.*
import java.util.logging.*
import javax.swing.*

class MainForm private constructor() : JFrame() {

    companion object {

        val INSTANCE: MainForm by lazy { MainForm() }

        val LOG: Logger = Logger.getLogger(MainForm::class.java.name)
    }

    private val button = JButton("Click me")
    val filePathField = JTextField(20)
    private val openButton = JButton("Open File")
    val settingsPanel = SettingsPanel()
    val centerPanel = JPanel()
    val settingsToggleButton = JButton("Open Advanced Settings")

    val uiData: UIData = UIData()
    private val listeners: UIListeners = UIListeners.INSTANCE

    init {
        this.title = "Data Processor"
        this.setSize(600, 600)
        this.minimumSize = Dimension(600, 300)
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setLocationRelativeTo(null)
        initUI()
        this.isVisible = true
    }

    override fun paint(g: Graphics?) {
        LOG.info("Painting main form")
        super.paint(g)
        LOG.info("Main form painted")

        settingsPanel.isVisible = uiData.isAdvancedSettingsVisible
        filePathField.text = uiData.filePath
    }

    fun initUI() {
        val panel = JPanel()
        panel.layout = BorderLayout()
        panel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        val topPanel = JPanel()
        topPanel.layout = BorderLayout()
        topPanel.add(filePathField, BorderLayout.CENTER)
        topPanel.add(openButton, BorderLayout.EAST)
        openButton.addActionListener(listeners.openFileListener())
        panel.add(topPanel, BorderLayout.NORTH)

        centerPanel.layout = BorderLayout()
        settingsToggleButton.text =
            if (uiData.isAdvancedSettingsVisible) "Close Advanced Settings" else "Open Advanced Settings"
        settingsToggleButton.addActionListener(listeners.toggleAdvancedSettingsListener())
        centerPanel.add(settingsToggleButton, BorderLayout.NORTH)
        centerPanel.add(settingsPanel, BorderLayout.CENTER)

        panel.add(centerPanel, BorderLayout.CENTER)
        panel.add(button, BorderLayout.SOUTH)

        add(panel)
    }

}