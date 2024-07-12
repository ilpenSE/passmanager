package dev.elite.Commands;

import dev.elite.Command;
import dev.elite.Main;
import dev.elite.User;

import static dev.elite.Main.*;

public class RegisterCmd implements Command {
    @Override
    public void onCommand(String[] args) {
        if (args.length == 0) {
            setOutputText("[ERROR] No username was provided");
            return;
        }
        if (args.length == 1) {
            setOutputText("[ERROR] No email was provided");
            return;
        }
        if (args.length == 2) {
            setOutputText("[ERROR] No password was provided");
            return;
        }
        if (args.length == 3) {
            setOutputText("[ERROR] No platform was provided");
            return;
        }
        if (!args[1].contains("@") && !args[1].equals("*")) {
            setOutputText("[ERROR] Email does not exist");
            return;
        }
        User user = new User(args[0], args[1], args[2], args[3]);
        PASSFILE.createUser(user);
        setOutputText("User has been successfully created");
    }
}
