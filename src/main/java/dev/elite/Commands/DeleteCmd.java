package dev.elite.Commands;

import dev.elite.Command;
import dev.elite.User;
import static dev.elite.Main.*;
public class DeleteCmd implements Command {
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
        String pwd = args[1];
        String platform = args[2];
        if (!User.nameOrEmailExists(val)) {
            setOutputText("[ERROR] No valid username or email was provided");
            return;
        }

        if (!User.passwordMatches(val, pwd, platform)) {
            setOutputText("[ERROR] Password does not match given username or email");
            return;
        }

        PASSFILE.deleteUser(val, pwd, platform);
        setOutputText("The user has been successfully deleted");
    }
}
