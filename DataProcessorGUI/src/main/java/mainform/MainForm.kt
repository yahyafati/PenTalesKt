package mainform

import settings.*
import java.awt.*
import javax.swing.*

class MainForm private constructor() : JFrame() {

    companion object {

        val INSTANCE: MainForm by lazy { MainForm() }

    }

    private val startButton = JButton("Start Processing")
    val filePathField = JTextField(20)
    private val openButton = JButton("Open File")
    val settingsPanel = SettingsPanel()
    val centerPanel = JPanel()
    val settingsToggleButton = JButton("Open Advanced Settings")

    val mainFormData: MainFormData = MainFormData()
    private val listeners: UIListeners = UIListeners.INSTANCE

    init {
        this.title = "Data Processor"
        this.setSize(600, 300)
        this.minimumSize = Dimension(600, 300)
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setLocationRelativeTo(null)
        initUI()
        this.isVisible = true
        this.addWindowListener(listeners.windowListener())
    }

    override fun paint(g: Graphics?) {
        super.paint(g)

        settingsPanel.isVisible = mainFormData.isAdvancedSettingsVisible
        filePathField.text = mainFormData.filePath
        startButton.text = if (mainFormData.isProcessing) "Pause Processing" else "Start Processing"
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
            if (mainFormData.isAdvancedSettingsVisible) "Close Advanced Settings" else "Open Advanced Settings"
        settingsToggleButton.addActionListener(listeners.toggleAdvancedSettingsListener())
        centerPanel.add(settingsToggleButton, BorderLayout.NORTH)
        centerPanel.add(settingsPanel, BorderLayout.CENTER)

        panel.add(centerPanel, BorderLayout.CENTER)

        startButton.addActionListener(listeners.startProcessingListener())
        panel.add(startButton, BorderLayout.SOUTH)

        add(panel)
    }

}