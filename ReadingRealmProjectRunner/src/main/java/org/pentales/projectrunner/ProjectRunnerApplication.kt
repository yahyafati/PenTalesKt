package org.pentales.projectrunner

import org.pentales.projectrunner.mainform.*
import org.pentales.projectrunner.util.*
import javax.swing.*

fun main() {
    System.setProperty("java.util.logging.SimpleFormatter.format", "%1\$tF %1\$tT %4\$s %5\$s%6\$s%n")
    AppHelper.copyConfigurationFiles()

    SwingUtilities.invokeLater {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val mainForm = MainForm()
        mainForm.isVisible = true
    }
}


