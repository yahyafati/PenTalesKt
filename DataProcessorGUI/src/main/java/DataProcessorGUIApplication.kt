import mainform.*
import javax.swing.*

fun main() {

//    execute on a separate thread using ExecutorService

    SwingUtilities.invokeLater {
        MainForm.INSTANCE
    }

}