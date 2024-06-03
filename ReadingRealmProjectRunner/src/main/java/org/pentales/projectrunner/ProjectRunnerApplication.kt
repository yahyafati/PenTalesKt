package org.pentales.projectrunner

import org.pentales.projectrunner.mainform.*
import org.pentales.projectrunner.util.*
import java.io.*
import java.nio.file.*
import javax.swing.*

fun main() {
    System.setProperty("java.util.logging.SimpleFormatter.format", "%1\$tF %1\$tT %4\$s %5\$s%6\$s%n")
    initFolders()

    FileUtils.copyResourceToFile("/docker-compose.yml", "${MainFormListeners.APP_DIR_NAME}/docker-compose.yml")
    FileUtils.copyResourceToFile("/backend.env", "${MainFormListeners.APP_DIR_NAME}/.env")
    FileUtils.copyResourceToFile("/frontend.env", "${MainFormListeners.APP_DIR_NAME}/frontend.env")
    FileUtils.copyResourceToFile("/keys/firebase-key.json", "${MainFormListeners.APP_DIR_NAME}/firebase-key.json")
    FileUtils.copyResourceToFile("/init.sql", "${MainFormListeners.APP_DIR_NAME}/init.sql")

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

fun initFolders() {
    val folders = arrayOf(
        MainFormListeners.APP_DIR_NAME,
        "${MainFormListeners.APP_DIR_NAME}/keys"
    )
    for (folder in folders) {
        Files.createDirectories(File(folder).toPath())
    }
}
