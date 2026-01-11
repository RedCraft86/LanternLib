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
    private boolean firstLoad = true;

    public BaseJsonCfg(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Config name must not be null or blank");
        }

        path = FMLPaths.CONFIGDIR.get().resolve(
            name.endsWith(".json") ? name : name + ".json"
        );

        loadJson();
    }

    public void loadJson() {
        if (!Files.exists(path)) {
            json = new JsonObject();
            saveJson();
            return;
        }

        try {
            String data = Files.readString(path);
            json = JsonParser.parseString(data);
            if (firstLoad) {
                firstLoad = false;
                validateJson();
            }
        } catch (JsonSyntaxException e) {
            LOGGER.error("Json config {} was malformed! Attempting to fix...", path.getFileName());
            validateJson();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config at: " + path.getFileName(), e);
        }
    }

    public void saveJson() {
        try {
            Files.writeString(path, GSON.toJson(json));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config at: " + path.getFileName(), e);
        }
    }

    public void validateJson() {
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
