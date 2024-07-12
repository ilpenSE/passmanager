package dev.elite.Commands;

import dev.elite.Command;
import dev.elite.Main;

public class AboutCmd implements Command {
    @Override
    public void onCommand(String[] args) {
        Main.setOutputText("<html><h2>PassManager</h2>Version: 1.0<br>Author: ilpeN, Elite Development<br><p>PassManager keeps your accounts on the computer. <br> If you don't know how to use this program, type \"help\" command to " +
                "get information.<br>The kept passwords are end-to-end encrypted.</p></html>");
    }
}
