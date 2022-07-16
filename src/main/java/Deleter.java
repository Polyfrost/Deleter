import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Deleter {
    private int tries = 0;
    private final JList<String> text = new JList<>(new String[]{"Deleter starting..."});
    private static File logFile = new File("skyclientupdater/files/deleterlog.txt");

    private Deleter() {

    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InterruptedException, InstantiationException, IllegalAccessException, IOException {
        Deleter deleter = new Deleter();
        deleter.run(args);
    }

    private void run(String[] files) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, IOException {
        if (!logFile.getParentFile().mkdirs()) {
            System.out.println("Failed to mkdirs, setting deleterlog.txt to root");
            logFile = new File("deleterlog.txt");
        }
        if (!logFile.createNewFile()) {
            System.out.println("Failed to create file, thing may not work");
        }
        append("+++ ARGS");
        for (String arg : files) {
            append("    " + arg);
        }
        append("--- ARGS");

        JFrame frame = new JFrame("Deleter 1.8");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JScrollPane scroll = new JScrollPane(text);
        scroll.setBounds(0, 0, 500, 500);
        frame.add(scroll);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        for (String file : files) {
            tries = 0;
            delete(new File(file));
        }
        append("Done! You can now close this window.");
        Thread.sleep(50000);
        System.exit(0);
    }

    private void delete(File file) throws InterruptedException {
        append("Checking if \"" + file.getAbsolutePath() + "\" exists");
        if (!file.exists()) {
            append("\"" + file.getAbsolutePath() + "\" does not exist!");
            return;
        }

        append("Deleting file \"" + file.getAbsolutePath() + "\"");

        if (!file.delete()) {
            tries += 1;
            if (tries >= 4) {
                append("\nToo many tries for \"" + file.getAbsolutePath() + "\", cancelling.");
                return;
            }
            append("\n\"" + file.getAbsolutePath() + "\" failed to delete, trying again in " + tries + " seconds...");
            Thread.sleep(1000L * tries);
            delete(file);
        } else {
            append("Successfully deleted \"" + file.getAbsolutePath() + "\"!");
        }
    }

    private void append(String string) {
        try (FileOutputStream fos = new FileOutputStream(logFile, true)) {
            fos.write((string + "\n").getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(string);
        ArrayList<String> list = getList();
        list.add(string);
        text.setModel(new JListList(list));
    }

    private ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        while (i < text.getModel().getSize()) {
            i++;
            list.add(text.getModel().getElementAt(i - 1));
        }
        return list;
    }
}

