package com.redcraft86.lanternlib.utils;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.*;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLPaths;

public abstract class JsonConfig {
    protected static final Logger LOGGER = LogUtils.getLogger();
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path filePath;
    private JsonObject json = null;
    private boolean isDirty = false;

    protected JsonConfig(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Config name must not be null or blank");
        }

        filePath = FMLPaths.CONFIGDIR.get().resolve(filename.contains(".") ? filename : filename + ".json");

        readJson();
    }

    protected Path getConfigPath() {
        return filePath;
    }

    protected JsonObject getOrCreateRoot() {
        if (json == null || !json.isJsonObject()) {
            json = new JsonObject();
        }
        return json;
    }

    protected JsonElement getJsonValue(String path) {
        String[] segments = path.split("\\.");
        JsonObject current = getOrCreateRoot();

        for (int i = 0; i < segments.length; i++) {
            JsonElement element = current.get(segments[i]);
            if (element == null) {
                return null;
            }
            if (i == segments.length - 1) {
                return element;
            }
            if (element.isJsonObject()) {
                current = element.getAsJsonObject();
            } else {
                return null;
            }
        }

        return null;
    }

    protected String getStringValue(String path, String defaultValue) {
        JsonElement value = getJsonValue(path);
        if (value != null && value.isJsonPrimitive()) {
            return value.getAsString();
        }

        isDirty = true;
        return defaultValue;
    }

    protected boolean getBoolValue(String path, boolean defaultValue) {
        JsonElement value = getJsonValue(path);
        if (value != null && value.isJsonPrimitive()) {
            return value.getAsBoolean();
        }

        isDirty = true;
        return defaultValue;
    }

    protected float getFloatValue(String path, float defaultValue) {
        JsonElement value = getJsonValue(path);
        if (value != null && value.isJsonPrimitive()) {
            return value.getAsFloat();
        }

        isDirty = true;
        return defaultValue;
    }

    protected int getIntValue(String path, int defaultValue) {
        JsonElement value = getJsonValue(path);
        if (value != null && value.isJsonPrimitive()) {
            return value.getAsInt();
        }

        isDirty = true;
        return defaultValue;
    }

    protected JsonArray getJsonArray(String path) {
        JsonElement value = getJsonValue(path);
        return (value != null && value.isJsonArray()) ? value.getAsJsonArray() : null;
    }

    protected List<String> getStringArray(String path, List<String> defaultValue) {
        JsonArray array = getJsonArray(path);
        if (array != null) {
            List<String> result = new ArrayList<>(array.size());
            array.forEach(entry -> {
                if (entry.isJsonPrimitive()) {
                    result.add(entry.getAsString());
                }
            });
            return result;
        }

        isDirty = true;
        return defaultValue;
    }

    protected List<Boolean> getBoolArray(String path, List<Boolean> defaultValue) {
        JsonArray array = getJsonArray(path);
        if (array != null) {
            List<Boolean> result = new ArrayList<>(array.size());
            array.forEach(entry -> {
                if (entry.isJsonPrimitive()) {
                    result.add(entry.getAsBoolean());
                }
            });
            return result;
        }

        isDirty = true;
        return defaultValue;
    }

    protected List<Float> getFloatArray(String path, List<Float> defaultValue) {
        JsonArray array = getJsonArray(path);
        if (array != null) {
            List<Float> result = new ArrayList<>(array.size());
            array.forEach(entry -> {
                if (entry.isJsonPrimitive()) {
                    result.add(entry.getAsFloat());
                }
            });
            return result;
        }

        isDirty = true;
        return defaultValue;
    }

    protected List<Integer> getIntArray(String path, List<Integer> defaultValue) {
        JsonArray array = getJsonArray(path);
        if (array != null) {
            List<Integer> result = new ArrayList<>(array.size());
            array.forEach(entry -> {
                if (entry.isJsonPrimitive()) {
                    result.add(entry.getAsInt());
                }
            });
            return result;
        }

        isDirty = true;
        return defaultValue;
    }

    protected void setJsonValue(String path, JsonElement value) {
        String[] segments = path.split("\\.");
        JsonObject current = getOrCreateRoot();

        isDirty = true;
        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];
            if (i == segments.length - 1) {
                current.add(segment, value);
            } else {
                JsonElement element = current.get(segment);
                if (element != null && element.isJsonObject()) {
                    current = element.getAsJsonObject();
                } else {
                    JsonObject newEntry = new JsonObject();
                    current.add(segment, newEntry);
                    current = newEntry;
                }
            }
        }
    }

    protected void setStringValue(String path, String value) {
        setJsonValue(path, new JsonPrimitive(value));
    }

    protected void setBoolValue(String path, boolean value) {
        setJsonValue(path, new JsonPrimitive(value));
    }

    protected void setFloatValue(String path, float value) {
        setJsonValue(path, new JsonPrimitive(value));
    }

    protected void setIntValue(String path, int value) {
        setJsonValue(path, new JsonPrimitive(value));
    }

    protected void setStringArray(String path, List<String> value) {
        JsonArray array = new JsonArray();
        value.forEach(array::add);
        setJsonValue(path, array);
    }

    protected void setBoolArray(String path, List<Boolean> value) {
        JsonArray array = new JsonArray();
        value.forEach(array::add);
        setJsonValue(path, array);
    }

    protected void setFloatArray(String path, List<Float> value) {
        JsonArray array = new JsonArray();
        value.forEach(array::add);
        setJsonValue(path, array);
    }

    protected void setIntArray(String path, List<Integer> value) {
        JsonArray array = new JsonArray();
        value.forEach(array::add);
        setJsonValue(path, array);
    }

    abstract protected void saveData();
    abstract protected void loadData();

    protected void writeJson() {
        isDirty = false;
        getOrCreateRoot();
        saveData();

        try {
            Files.writeString(filePath, GSON.toJson(json));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config at: " + filePath.getFileName(), e);
        }
    }

    protected void readJson() {
        if (!Files.exists(filePath)) {
            loadData();
            writeJson();
            return;
        }

        try {
            String data = Files.readString(filePath);
            JsonElement parsed = JsonParser.parseString(data);
            if (parsed.isJsonObject()) {
                json = parsed.getAsJsonObject();
            } else {
                throw new JsonSyntaxException("Json was not parsed as a json object.");
            }

            loadData();
            if (isDirty) {
                writeJson();
            }
        } catch (JsonSyntaxException e) {
            LOGGER.error("Json config {} was malformed! Attempting to fix by resetting...", filePath.getFileName());
            loadData();
            writeJson();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config at: " + filePath.getFileName(), e);
        }
    }
}
