package settings

import java.awt.*
import java.util.logging.*
import javax.swing.*

class SettingsPanel : JPanel() {

    companion object {

        val LOG: Logger = Logger.getLogger(SettingsPanel::class.java.name)

        val INSTANCE: SettingsPanel by lazy { SettingsPanel() }
    }

    val settingsUIData: SettingsData = SettingsData()

    val minimumRatingTextField = JTextField(settingsUIData.minimumRating.toString(), 10)
    val sleepIntervalTextField = JTextField(settingsUIData.sleepInterval.toString(), 10)
    val sleepDurationTextField = JTextField(settingsUIData.sleepDuration.toString(), 10)
    val startFromTextField = JTextField(settingsUIData.startFrom.toString(), 10)
    val endAtTextField = JTextField(settingsUIData.endAt.toString(), 10)
    val saveButton = JButton("Save Settings")

    private val settingsListeners: SettingsListeners = SettingsListeners.INSTANCE

    init {
        this.layout = BorderLayout()

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