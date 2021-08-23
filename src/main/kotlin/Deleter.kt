import org.jetbrains.annotations.NotNull
import java.io.File
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.WindowConstants
import kotlin.system.exitProcess


var file: File? = null
var delete = ""
val frame = JFrame("Deleter")
var tries = 0
fun main(@NotNull args : Array<String>) {

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    frame.setSize(500, 500)
    frame.isVisible = true
    frame.isFocusable = true
    frame.setLocationRelativeTo(null)
    frame.isResizable = false
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    delete = args[0]
    println(delete)
    file = File(delete)
    delete()
    Thread.sleep(1000)
    exitProcess(0)
}

private fun delete() {
    println(file?.path)
    if (!file?.exists()!!) {
        println("the file doesn't exist lol")
        return
    }
    if (!file!!.delete()) {
        tries += 1
        if (tries >= 4) {
            println("ok i give up")
            return
        }
        println("file failed to delete... trying again...")
        Thread.sleep((1000 * tries).toLong())
        delete()
    }
}
