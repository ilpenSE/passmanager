package dev.elite;

import java.util.HashMap;

public class CommandHandler {
    private final HashMap<String, Command> COMMANDS;
    public CommandHandler() {
        COMMANDS = new HashMap<>();
    }

    public void registerCommand(String cmd, Command executor) {
        COMMANDS.put(cmd, executor);
    }

    public void executeCommand(String cmdString) {
        String[] src = cmdString.split(" ");
        String[] args = Main.removeElements(src,src[0]);
        String cmd = src[0];
        if (cmdString.isEmpty() || !COMMANDS.containsKey(cmd)) {
            Main.setOutputText("[ERROR] Please type a valid command");
            return;
        }
        Main.FORM.tpanel.setVisible(false);
        COMMANDS.get(cmd).onCommand(args);
    }
}
