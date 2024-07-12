package dev.elite.Commands;

import dev.elite.Command;
import dev.elite.Main;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static dev.elite.Main.*;

public class GetpassCmd implements Command {
    @Override
    public void onCommand(String[] args) {
        if (args.length == 0) {
            setOutputText("[ERROR] No username or email was provided");
            return;
        }

        if (args.length == 1) {
            setOutputText("[ERROR] No platform was provided");
            return;
        }
        String output = Main.PASSFILE.getPassword(args[0], args[1]);
        setOutputText(output == null ? "[ERROR] No profile does not match" :
                "<html>Password: " + output + "<br></br>(Copy to clipboard)</html>");

        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(output),null);
                setOutputText(output == null ? "[ERROR] No profile does not match" :
                        "<html>Password: " + output + "<br></br><p style=\"color: green;\">(Copied to clipboard!)</p></html>");
                FORM.output.removeMouseListener(this);
            }
        };
        FORM.output.addMouseListener(l);
    }
}
