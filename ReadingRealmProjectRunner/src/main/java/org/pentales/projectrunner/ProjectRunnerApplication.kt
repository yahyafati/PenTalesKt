package org.commandlink.server.org.pentales.projectrunner

import org.pentales.projectrunner.mainform.*
import java.io.*
import java.nio.file.*
import javax.swing.*

fun main() {
    System.setProperty("java.util.logging.SimpleFormatter.format", "%1\$tF %1\$tT %4\$s %5\$s%6\$s%n")
    initFolders()
    SwingUtilities.invokeLater {
        val mainForm = MainForm()
        mainForm.isVisible = true
    }
}

fun initFolders() {
    val folders = arrayOf("backend", "frontend")
    for (folder in folders) {
        Files.createDirectories(File(folder).toPath())
    }
}
