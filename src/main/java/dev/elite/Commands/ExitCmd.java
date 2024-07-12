package dev.elite.Commands;

import dev.elite.Command;

public class ExitCmd implements Command {
    @Override
    public void onCommand(String[] args) {
        System.exit(0);
    }
}
