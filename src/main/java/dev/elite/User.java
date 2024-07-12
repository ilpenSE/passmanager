package dev.elite;

import java.util.Objects;
import java.util.stream.Stream;

import static dev.elite.Main.*;

public class User {
    private final String username;
    private final String email;
    private final String pwd;
    private final String platform;
    public User(String username, String email, String pwd, String platform) {
        this.email = email;
        this.username = username;
        this.pwd = pwd;
        this.platform = platform;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return pwd;
    }

    public String getUsername() {
        return username;
    }

    public String getPlatform() {
        return platform;
    }

    public static boolean nameOrEmailExists(String val) {
        for (String s : Main.PASSFILE.getLines()) {
            if (s.split(FILE_SEPARATOR)[val.contains("@") ? 1 : 0].equals(val)) {
                return true;
            }
        }
        return false;
    }

    public static boolean passwordMatches(String val, String pwd, String platform) {
        for (String s : Main.PASSFILE.getLines()) {
            String[] values = s.split(FILE_SEPARATOR);
            if (values[val.contains("@") ? 1 : 0].equals(val) && values[3].equals(platform)) {
                return Objects.equals(pwd, values[2]);
            }
        }
        return false;
    }

    public static boolean platformMatches(String val,String platform) {
        for (String s : PASSFILE.getLines()) {
            String[] values = s.split(FILE_SEPARATOR);
            if (values[val.contains("@") ? 1 : 0].equals(val) && values[3].equals(platform)) {
                return true;
            }
        }
        return false;
    }
}
