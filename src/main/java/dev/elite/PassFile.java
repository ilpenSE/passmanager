package dev.elite;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import static dev.elite.Main.*;
public class PassFile {
    private final File file;
    private FileOperations fileOps;
    private ArrayList<String> lines;
    public PassFile(File f) {
        this.file = f;
        fileOps = new FileOperations(file);
        lines = new ArrayList<>();
        for (String str : fileOps.readAllLines()) {
            String[] s = str.split(FILE_SEPARATOR);
            lines.add(s[0] + FILE_SEPARATOR + s[1] + FILE_SEPARATOR + Main.b64decode(s[2]) + FILE_SEPARATOR + s[3]);
        }
    }

    public File getFile() {
        return file;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public String getPassword(String key, String platform) {
        for (String s : lines) {
            String[] args = s.split(FILE_SEPARATOR);
            if (args[key.contains("@") ? 1 : 0].equals(key) && args[3].equals(platform)) {
                return args[2];
            }
        }
        return null;
    }
    
    public void editUser(String val, String newPwd, String platform) {
        User user = null;
        String oldLine = "";
        for (final Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
            String s = iterator.next();
            String[] cred = s.split(FILE_SEPARATOR);
            if (cred[val.contains("@") ? 1 : 0].equals(val)) {
                oldLine = cred[0] + FILE_SEPARATOR + cred[1] + FILE_SEPARATOR + b64encode(cred[2]) + FILE_SEPARATOR + platform;
                iterator.remove();
                user = new User(cred[0], cred[1], newPwd, platform);
            }
        }
        String nLine = user.getUsername() + FILE_SEPARATOR + user.getEmail() + FILE_SEPARATOR;
        lines.add(nLine + user.getPassword() + FILE_SEPARATOR + platform);
        fileOps.editLine(oldLine, nLine + Main.b64encode(user.getPassword()) + FILE_SEPARATOR + platform);
    }

    public void createUser(User user) {
        String l = user.getUsername() + FILE_SEPARATOR + user.getEmail() + FILE_SEPARATOR;
        lines.add(l + user.getPassword() + FILE_SEPARATOR + user.getPlatform());
        fileOps.appendLine(l + Main.b64encode(user.getPassword()) + FILE_SEPARATOR + user.getPlatform());
    }

    public void deleteUser(String val, String pwd, String platform) {
        String oldLine = "";
        for (final Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
            String s = iterator.next();
            String[] cred = s.split(FILE_SEPARATOR);
            if (cred[val.contains("@") ? 1 : 0].equals(val) && cred[2].equals(pwd) && cred[3].equals(platform)) {
                oldLine = cred[0] + FILE_SEPARATOR + cred[1] + FILE_SEPARATOR + b64encode(cred[2]) + FILE_SEPARATOR + platform;
                iterator.remove();
            }
        }
        fileOps.removeLine(oldLine);
    }
}
