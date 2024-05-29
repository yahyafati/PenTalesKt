package org.readingrealm.dataProcessor.mainform

import org.readingrealm.dataProcessor.settings.*
import java.awt.*
import java.util.logging.*
import javax.swing.*

class MainForm private constructor() : JFrame() {

    companion object {

        private var INSTANCE: MainForm? = null
            set(value) {
                if (field == null) {
                    field = value
                } else {
                    throw IllegalStateException("MainForm already created")
                }
            }

        private val LOG = Logger.getLogger(MainForm::class.java.name)

        val instance: MainForm
            get() {
                if (INSTANCE == null) {
                    INSTANCE = MainForm()
                }
                return INSTANCE!!
            }

    }

    val mainFormData: MainFormData = MainFormData.instance

    val startButton = JButton("Start Processing")
    val statusLabel = JLabel("Status: Not Processing")
    val filePathField = JTextField(mainFormData.filePath, 20)
    private val openButton = JButton("Open File")
    val settingsPanel = SettingsPanel.instance
    val centerPanel = JPanel()
    val settingsToggleButton = JButton("Open Advanced Settings")

    private val listeners: MainFormListeners = MainFormListeners.instance

    init {
        LOG.info("Loaded $this")
        this.title = "Data Processor"
        this.setSize(600, 300)
        this.minimumSize = Dimension(600, 120)
        this.defaultCloseOperation = DO_NOTHING_ON_CLOSE
        this.setLocationRelativeTo(null)
        initUI()
        this.isVisible = true
        this.addWindowListener(listeners.windowListener())
        this.pack()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)

        settingsPanel.isVisible = mainFormData.isAdvancedSettingsVisible
        filePathField.text = mainFormData.filePath
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
//        centerPanel.add(settingsToggleButton, BorderLayout.NORTH)
//        centerPanel.add(settingsPanel, BorderLayout.CENTER)

        panel.add(centerPanel, BorderLayout.CENTER)

        startButton.addActionListener(listeners.startProcessingListener())

        val bottomPanel = JPanel()
        bottomPanel.layout = BorderLayout()
        bottomPanel.add(startButton, BorderLayout.WEST)
        bottomPanel.add(statusLabel, BorderLayout.CENTER)
        panel.add(bottomPanel, BorderLayout.SOUTH)

        add(panel)
    }

}