package settings

import java.awt.*
import java.util.logging.*
import javax.swing.*

class SettingsPanel private constructor() : JPanel() {

    companion object {

        val LOG: Logger = Logger.getLogger(SettingsPanel::class.java.name)

        private var INSTANCE: SettingsPanel? = null

        val instance: SettingsPanel
            get() {
                LOG.info("SettingsPanel: $INSTANCE")
                if (INSTANCE == null) {
                    LOG.info("Creating new SettingsPanel")
                    INSTANCE = SettingsPanel()
                }
                return INSTANCE as SettingsPanel
            }

    }

    val minimumRatingTextField = JTextField("3000", 10)
    val sleepIntervalTextField = JTextField(10)
    val sleepDurationTextField = JTextField(10)
    val startFromTextField = JTextField(10)
    val endAtTextField = JTextField(10)
    private val saveButton = JButton("Save Settings")

    private val settingsListeners: SettingsListeners = SettingsListeners.instance

    init {
        LOG.info("SettingsPanel: $this")
        this.layout = BorderLayout()

        this.minimumRatingTextField.text = SettingsData.instance.minimumRating.toString()
        this.sleepIntervalTextField.text = SettingsData.instance.sleepInterval.toString()
        this.sleepDurationTextField.text = SettingsData.instance.sleepDuration.toString()
        this.startFromTextField.text = SettingsData.instance.startFrom.toString()
        this.endAtTextField.text = SettingsData.instance.endAt.toString()

        val contentPanel = JPanel()
        contentPanel.layout = BoxLayout(contentPanel, BoxLayout.Y_AXIS)

        val minimumRatingPanel = JPanel()
        minimumRatingPanel.layout = BoxLayout(minimumRatingPanel, BoxLayout.X_AXIS)
        minimumRatingPanel.add(JLabel("Minimum Rating: "))
        minimumRatingPanel.add(minimumRatingTextField)

        val sleepPanel = JPanel()
        val sleepIntervalPanel = JPanel()
        sleepIntervalPanel.layout = BoxLayout(sleepIntervalPanel, BoxLayout.X_AXIS)
        sleepIntervalPanel.add(JLabel("Sleep Interval: "))
        sleepIntervalTextField.toolTipText = "Sleep every x requests"
        sleepIntervalPanel.add(sleepIntervalTextField)
        sleepPanel.add(sleepIntervalPanel)

        val sleepLengthPanel = JPanel()
        sleepLengthPanel.layout = BoxLayout(sleepLengthPanel, BoxLayout.X_AXIS)
        sleepLengthPanel.add(JLabel("Sleep Duration: "))
        sleepDurationTextField.toolTipText = "The duration of the sleep in minutes"
        sleepLengthPanel.add(sleepDurationTextField)
        sleepPanel.add(sleepLengthPanel)

        val rangePanel = JPanel()
        rangePanel.layout = BoxLayout(rangePanel, BoxLayout.X_AXIS)

        val startFromPanel = JPanel()
        startFromPanel.layout = BoxLayout(startFromPanel, BoxLayout.X_AXIS)
        startFromPanel.add(JLabel("Start From: "))
        startFromPanel.add(startFromTextField)

        val endAtPanel = JPanel()
        endAtPanel.layout = BoxLayout(endAtPanel, BoxLayout.X_AXIS)
        endAtPanel.add(JLabel("End At: "))
        endAtPanel.add(endAtTextField)

        rangePanel.add(startFromPanel)
        rangePanel.add(endAtPanel)

        val buttonPanel = JPanel()
        buttonPanel.layout = BoxLayout(buttonPanel, BoxLayout.X_AXIS)
        saveButton.addActionListener(settingsListeners.saveSettingsListener())
        buttonPanel.add(saveButton)


        contentPanel.add(minimumRatingPanel)
        contentPanel.add(sleepPanel)
        contentPanel.add(rangePanel)
        contentPanel.add(buttonPanel)

        this.add(contentPanel, BorderLayout.NORTH)
    }
}