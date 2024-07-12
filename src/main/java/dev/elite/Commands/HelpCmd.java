package dev.elite.Commands;

import dev.elite.Command;

import java.awt.*;

import static dev.elite.Main.*;

public class HelpCmd implements Command {
    @Override
    public void onCommand(String[] args) {
        String out = "<html>";
        if (args.length == 0) {
            out += "<h2>Commands</h2>";
            for (String cmd : CONFIG.getCommands()) {
                out += cmd + ": " + CONFIG.getDescription(cmd) + "<br>";
            }
        } else {
            String comm = args[0];
            if (!CONFIG.commandExists(comm)) {
                out = "[ERROR] Command does not exist";
                FORM.output.setText(out);
                return;
            }
            out += "<h2>" + comm + " Command</h2>";
            out += "Usage: " + CONFIG.getUsage(comm) + "<br>";
            out += "Description: " + CONFIG.getDescription(comm) + "<br>";
        }
        FORM.output.setText(out + "</html>");
        FORM.output.setForeground(Color.WHITE);
    }
}
