package org.readingrealm.dataProcessor

import org.readingrealm.dataProcessor.mainform.*
import javax.swing.*

fun main() {

    System.setProperty("java.util.logging.SimpleFormatter.format", "%1\$tF %1\$tT %4\$s %5\$s%6\$s%n")

    SwingUtilities.invokeLater {
        MainForm.instance
    }

}