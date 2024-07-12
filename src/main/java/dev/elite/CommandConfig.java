package dev.elite;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandConfig {
    private final File configFile;
    private Gson GSON;
    private HashMap<String, JsonObject> COMMANDS;
    public CommandConfig(File configFile) {
        this.configFile = configFile;
        GSON = new Gson();
        Type t = new TypeToken<HashMap<String, JsonObject>>() {}.getType();
        try {
            COMMANDS = GSON.fromJson(new FileReader(configFile),t);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean commandExists(String cmd) {
        return getCommands().contains(cmd);
    }

    public ArrayList<String> getCommands() {
        return new ArrayList<>(COMMANDS.keySet());
    }

    public String getUsage(String cmd) {
        return COMMANDS.get(cmd).get("usage").getAsString();
    }

    public String getDescription(String cmd) {
        return COMMANDS.get(cmd).get("description").getAsString();
    }
}
