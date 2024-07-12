package dev.elite.Commands;

import dev.elite.Command;
import dev.elite.Main;
import dev.elite.User;

import static dev.elite.Main.*;

public class SetpassCmd implements Command {
    @Override
    public void onCommand(String[] args) {
        if (args.length == 0) {
            setOutputText("[ERROR] No username or email was provided");
            return;
        }

        if (args.length == 1) {
            setOutputText("[ERROR] No password was provided");
            return;
        }

        if (args.length == 2) {
            setOutputText("[ERROR] No platform was provided");
            return;
        }

        String val = args[0];
        String newPwd = args[1];
        String platform = args[2];
        if (!User.nameOrEmailExists(val)) {
            setOutputText("[ERROR] Provided username or email does not exist");
            return;
        }

        if (!User.platformMatches(val, platform)) {
            setOutputText("[ERROR] No profile matched with provided username/email and platform");
            return;
        }

        PASSFILE.editUser(val, newPwd, platform);
        setOutputText("Password has been successfully set");
    }
}
