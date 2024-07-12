package dev.elite.UI;

import dev.elite.Main;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Form1 extends JFrame {
    private JPanel panel;
    private JTextField input;
    public JLabel output;
    public JScrollPane tpanel;

    public Form1() {
        if (panel == null) {
            panel = new JPanel();
        }
        add(panel);
        setTitle("PassManager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,460);
        setLocationRelativeTo(null);
        output.setVisible(false);
        tpanel.setVisible(false);

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("icon.png")) {
            File f = File.createTempFile("iconpng",".tmp");
            assert is != null;
            FileUtils.copyInputStreamToFile(is,f);
            setIconImage(new ImageIcon(f.getAbsolutePath()).getImage());

            f.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Main.COMMAND_HANDLER.executeCommand(input.getText());
                    output.setVisible(true);
                    input.setText("");
                }
            }
        });
    }
}
