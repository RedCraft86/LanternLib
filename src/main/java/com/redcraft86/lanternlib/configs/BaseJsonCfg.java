package com.redcraft86.lanternlib.configs;

import com.google.gson.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLPaths;

public abstract class BaseJsonCfg {
    protected static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path path;
    private JsonElement json = null;

    public BaseJsonCfg(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Config name must not be null or blank");
        }

        path = FMLPaths.CONFIGDIR.get().resolve(
            name.contains(".") ? name : name + ".json"
        );

        loadJson();
    }

    public void loadJson() {
        if (!Files.exists(path)) {
            resetJson();
            return;
        }

        try {
            String data = Files.readString(path);
            json = JsonParser.parseString(data);
            if (json.isJsonObject()) {
                fromJson(json.getAsJsonObject());
            } else {
                resetJson();
            }
        } catch (JsonSyntaxException e) {
            LOGGER.error("Json config {} was malformed! Attempting to fix...", path.getFileName());
            resetJson();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config at: " + path.getFileName(), e);
        }
    }

    public void saveJson() {
        if (json.isJsonObject()) {
            toJson(json.getAsJsonObject());
        } else {
            json = new JsonObject();
        }

        try {
            Files.writeString(path, GSON.toJson(json));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config at: " + path.getFileName(), e);
        }
    }

    abstract protected void loadDefault();
    abstract protected void fromJson(JsonObject data);
    abstract protected void toJson(JsonObject data);
    protected void resetJson() {
        json = new JsonObject();
        loadDefault();
        saveJson();
    }

    protected Path getPath() {
        return path;
    }

    @Nullable
    protected JsonElement getJson() {
        return json;
    }
}
