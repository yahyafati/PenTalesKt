package settings

import java.awt.*
import javax.swing.*

class SettingsPanel : JPanel() {

    /*
     * So the panel will have a list of text field options (MTextField) and a button to save the settings
     *
     * The text field options will be:
     *  1. Minimum Rating
     *  2. Sleep Interval
     *  3. Start From
     *  4. End At
     *
     */

    private val minimumRatingTextField = JTextField(20)
    private val sleepIntervalTextField = JTextField(10)
    private val sleepLengthTextField = JTextField(10)
    private val startFromTextField = JTextField(20)
    private val endAtTextField = JTextField(20)
    private val saveButton = JButton("Start Processing")
    private val sleepButton = JButton("Sleep")

    init {
        this.layout = BorderLayout()

        val contentPanel = JPanel()
        contentPanel.layout = BoxLayout(contentPanel, BoxLayout.Y_AXIS)

        val minimumRatingPanel = JPanel()
        minimumRatingPanel.layout = BoxLayout(minimumRatingPanel, BoxLayout.X_AXIS)
        minimumRatingPanel.add(JLabel("Minimum Rating: "))
        minimumRatingPanel.add(minimumRatingTextField)

        val sleepPanel = JPanel()
        val sleepLengthPanel = JPanel()
        sleepLengthPanel.layout = BoxLayout(sleepLengthPanel, BoxLayout.X_AXIS)

        sleepLengthPanel.add(JLabel("Sleep Length: "))
        sleepLengthPanel.add(sleepLengthTextField)
        sleepPanel.add(sleepLengthPanel)

        val sleepIntervalPanel = JPanel()
        sleepIntervalPanel.layout = BoxLayout(sleepIntervalPanel, BoxLayout.X_AXIS)
        sleepIntervalPanel.add(JLabel("Sleep Interval: "))
        sleepIntervalPanel.add(sleepIntervalTextField)
        sleepPanel.add(sleepIntervalPanel)

        val startFromPanel = JPanel()
        startFromPanel.layout = BoxLayout(startFromPanel, BoxLayout.X_AXIS)
        startFromPanel.add(JLabel("Start From: "))
        startFromPanel.add(startFromTextField)

        val endAtPanel = JPanel()
        endAtPanel.layout = BoxLayout(endAtPanel, BoxLayout.X_AXIS)
        endAtPanel.add(JLabel("End At: "))
        endAtPanel.add(endAtTextField)

        val buttonPanel = JPanel()
        buttonPanel.layout = BoxLayout(buttonPanel, BoxLayout.X_AXIS)
        buttonPanel.add(saveButton)
        buttonPanel.add(sleepButton)

        contentPanel.add(minimumRatingPanel)
        contentPanel.add(sleepPanel)
        contentPanel.add(startFromPanel)
        contentPanel.add(endAtPanel)
        contentPanel.add(buttonPanel)

        this.add(contentPanel, BorderLayout.NORTH)

    }
}