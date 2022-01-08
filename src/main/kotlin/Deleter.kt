import java.io.File
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.WindowConstants
import kotlin.system.exitProcess

var tries = 0
val frame = JFrame("Deleter")

fun main(args : Array<String>) {

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    frame.setSize(500, 500)
    frame.isVisible = true
    frame.isFocusable = true
    frame.setLocationRelativeTo(null)
    frame.isResizable = false
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    for (file in args) {
        tries = 0
        delete(File(file))
    }
    Thread.sleep(1000)
    exitProcess(0)
}

private fun delete(file: File) {
    if (!file.exists()) {
        println("That file does not exist!")
        return
    }
    if (!file.delete()) {
        tries += 1
        if (tries >= 4) {
            println("Too many tries, cancelling.")
            return
        }
        println("File failed to delete, trying again.")
        Thread.sleep((1000 * tries).toLong())
        delete(file)
    }
}
