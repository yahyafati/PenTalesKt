package org.pentales.projectrunner.mainform.logPanel.serviceTab

import java.awt.*
import javax.swing.*
import javax.swing.event.*

class ServiceTab(
    val serviceName: String,
    private val hideStartStopButtons: Boolean = false
) : JPanel() {

    enum class Status {
        STARTED,
        STARTING,
        STOPPED,
        STOPPING,
    }

    val textArea = JTextArea()
    private var anchorToBottom = true
    private val startLoggingButton = JButton("Start Logging")
    private val stopLoggingButton = JButton("Stop Logging")
    private val anchorToBottomButton = JToggleButton("Anchor", anchorToBottom)
    private val scrollPane = JScrollPane(textArea)

    private val listener = ServiceTabListener(this)

    var status = Status.STOPPED
        set(value) {
            field = value
            repaint()
        }

    init {
        scrollPane.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        scrollPane.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED

        textArea.isEditable = false
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.font = UIManager.getFont("Label.font")
        textArea.border = UIManager.getBorder("Label.border")
        textArea.font = Font("Monospaced", Font.PLAIN, 12)


        scrollPane.setViewportView(textArea)
        scrollPane.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        initUI()
        initListeners()
    }

    private fun initListeners() {
        startLoggingButton.addActionListener {
            listener.startLoggingListener()
        }

        stopLoggingButton.addActionListener {
            listener.stopLoggingListener()
        }

        anchorToBottomButton.addActionListener {
            anchorToBottom = anchorToBottomButton.isSelected
            if (anchorToBottom) {
                scrollToEnd()
            }
        }

        textArea.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent?) {
                scrollToEnd()
            }

            override fun removeUpdate(e: DocumentEvent?) {
                scrollToEnd()
            }

            override fun changedUpdate(e: DocumentEvent?) {
                scrollToEnd()
            }

        })
    }

    private fun scrollToEnd() {
        if (anchorToBottom) {
            SwingUtilities.invokeLater { textArea.caretPosition = textArea.document.length }
        }
    }

    override fun paint(g: Graphics?) {
        super.paint(g)

        startLoggingButton.isEnabled = status == Status.STOPPED
        stopLoggingButton.isEnabled = status in listOf(Status.STARTED, Status.STARTING)
    }

    private fun initUI() {
        layout = BorderLayout()

        val label =
            JLabel("<html>Full log for $serviceName can be found below in <b>$serviceName.log</b></html>").apply {
                isOpaque = true
                background = Color(0, 0, 0, 0)
                border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            }
        label.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        val northPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.LINE_AXIS)

            add(anchorToBottomButton)
            if (!hideStartStopButtons) {
                add(startLoggingButton)
                add(stopLoggingButton)
            }
            add(label)
        }

        add(northPanel, BorderLayout.NORTH)
        add(scrollPane, BorderLayout.CENTER)
    }
}