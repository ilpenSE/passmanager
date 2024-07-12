package dev.elite;

import dev.elite.Commands.*;
import dev.elite.UI.Form1;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

/**
 * <h1>PassManager</h1>
 * @author ilpeN, Elite Development
 * @version 1.0
 * <p>PassManager keeps your accounts on the computer. If you don't know how to use this program, type "help" command to
 * get information. The kept passwords are encrypted.</p>
 */
public class Main {
    public static CommandConfig CONFIG;
    public static PassFile PASSFILE;
    public static String FILE_SEPARATOR = "_";
    public static CommandHandler COMMAND_HANDLER;
    public static Form1 FORM;
    public static void main(String[] args) {
        if (args.length == 0) {
            JOptionPane.showMessageDialog(null, "You cannot open this .jar file directly.", "PassManager", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String path = args[0].split("=")[1];
        if (!new File(path).exists()) {
            JOptionPane.showMessageDialog(null, "You cannot open this .jar file directly.", "PassManager", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("commands.json")) {
            File f = File.createTempFile("commandsjson",".tmp");
            assert is != null;
            FileUtils.copyInputStreamToFile(is,f);

            COMMAND_HANDLER = new CommandHandler();
            CONFIG = new CommandConfig(f);
            PASSFILE = new PassFile(new File(path));
            FORM = new Form1();
            launch();

            f.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> FORM.setVisible(true));
        registerAllCommands(COMMAND_HANDLER);
    }

    public static void registerAllCommands(CommandHandler handler) {
        handler.registerCommand("help", new HelpCmd());
        handler.registerCommand("getpass", new GetpassCmd());
        handler.registerCommand("exit", new ExitCmd());
        handler.registerCommand("setpass", new SetpassCmd());
        handler.registerCommand("register", new RegisterCmd());
        handler.registerCommand("delete", new DeleteCmd());
        handler.registerCommand("about", new AboutCmd());
    }

    public static String b64encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String b64decode(String code) {
        return new String(Base64.getDecoder().decode(code.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String[] removeElements(String[] input, String deleteMe) {
        if (input != null) {
            List<String> list = new ArrayList<String>(Arrays.asList(input));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(deleteMe)) {
                    list.remove(i);
                }
            }
            return list.toArray(new String[0]);
        } else {
            return new String[0];
        }
    }

    public static void setOutputText(String txt) {
        if (txt.startsWith("[ERROR]")) {
            FORM.output.setForeground(new Color(Color.RED.getRGB()));
        } else {
            FORM.output.setForeground(new Color(Color.WHITE.getRGB()));
        }
        FORM.output.setText(txt);
    }
}