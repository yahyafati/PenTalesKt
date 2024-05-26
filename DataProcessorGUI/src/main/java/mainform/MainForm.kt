package mainform

import settings.*
import java.awt.*
import javax.swing.*

class MainForm private constructor() : JFrame() {

    companion object {

        val INSTANCE: MainForm
            get() = MainForm()
    }

    private val button = JButton("Click me")
    private val textField = JTextField(20)
    private val openButton = JButton("Open File")
    private val settingsPanel = SettingsPanel()
    private val settingsToggleButton = JButton("Advanced Settings")

    init {
        this.title = "Data Processor"
        this.setSize(800, 600)
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setLocationRelativeTo(null)
        initUI()
        this.pack()
        this.isResizable = false
        this.isVisible = true
    }

    fun initUI() {
        val panel = JPanel()
        panel.layout = BorderLayout()
        panel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        val topPanel = JPanel()
        topPanel.layout = BorderLayout()
        topPanel.add(textField, BorderLayout.CENTER)
        topPanel.add(openButton, BorderLayout.EAST)
        panel.add(topPanel, BorderLayout.NORTH)

        val centerPanel = JPanel()
        centerPanel.layout = BorderLayout()
        centerPanel.add(settingsToggleButton, BorderLayout.NORTH)
        centerPanel.add(settingsPanel, BorderLayout.CENTER)

        panel.add(centerPanel, BorderLayout.CENTER)
        panel.add(button, BorderLayout.SOUTH)

        add(panel)
    }

}