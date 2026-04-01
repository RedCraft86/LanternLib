package com.redcraft86.lanternlib.api.config;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.*;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLPaths;
import com.redcraft86.lanternlib.api.config.annotations.*;

public abstract class JsonConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected static final String TAB_KEY = "{TABS}";

    private final Path filePath;
    private final Path backupPath;
    private boolean fileExist = false;
    private boolean isDirty = false;

    public static <T extends JsonConfig> T createConfig(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            T inst = clazz.getDeclaredConstructor().newInstance();
            inst.readFile();
            return inst;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOGGER.error("Failed to create config for: {}", clazz.getSimpleName(), e);
            return null;
        }
    }

    protected JsonConfig(String filename) {
        if (filename == null || filename.isBlank()) {
            filename = getClass().getSimpleName();
            LOGGER.warn("Filename for {} is invalid, using class name", filename);
        }
        if (filename.contains(".")) {
            filename = filename.substring(0, filename.lastIndexOf("."));
            LOGGER.warn("Filename for {} contains an extension, changing to {}", getClass().getSimpleName(), filename);
        }

        filePath = FMLPaths.CONFIGDIR.get().resolve(filename + ".json5");
        backupPath = Path.of(filePath + ".bak");
    }

    public JsonConfig() {
        this(null);
    }

    public void markDirty() {
        isDirty = true;
    }

    public final Path getPath() {
        return filePath;
    }

    public void resetFile() {
        try {
            String original = Files.readString(filePath);
            if (!original.isBlank()) {
                Files.writeString(backupPath, original);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to backup config {} before resetting. Old file will be lost.", this, e);
        }

        writeFile();
    }

    public void readFile() {
        if (!Files.exists(filePath)) {
            writeFile();
            return;
        }

        fileExist = true;
        try {
            String rawStr = Files.readString(filePath);

            // Remove comments
            StringBuilder builder = new StringBuilder();
            String[] lines = rawStr.split("\n");
            for (String line : lines) {
                String cleanLine = line.trim();
                if (!cleanLine.startsWith("//")) {
                    builder.append(cleanLine);
                }
            }

            String jsonStr = builder.toString()
                .replace(",}", "}")  // Fix up trailing commas from braces
                .replace(",]", "]"); // ... and brackets too

            // Parse values back into the class and check to see if it needs re-saving
            deserialize(jsonStr);
            if (isDirty) {
                writeFile();
            } else {
                LOGGER.info("Loaded config: {}", this);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read config: {}", this, e);
        }
    }

    public void writeFile() {
        try {
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, serialize());
            isDirty = false;

            if (!fileExist) {
                fileExist = true;
                LOGGER.info("Created config: {}", this);
            } else {
                LOGGER.info("Updated config: {}", this);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to write config: {}", this, e);
        }
    }

    protected void postDeserialize(JsonObject root) {}
    protected void deserialize(String jsonStr) {
        try {
            JsonElement parsed = JsonParser.parseString(jsonStr);
            if (!parsed.isJsonObject()) {
                LOGGER.error("Failed to parse config {} due to invalid format, resetting...", this);
                resetFile();
                return;
            }

            JsonObject root = parsed.getAsJsonObject();
            for (Field field : getClass().getDeclaredFields()) {
                Config cfg = field.getAnnotation(Config.class);
                if (cfg == null) {
                    continue;
                }

                JsonElement element = getJsonAtPath(root, cfg.value()).get(field.getName());
                if (element != null) {
                    jsonToField(field, element);
                } else {
                    markDirty();
                }
            }

            postDeserialize(root);
        } catch (JsonSyntaxException e) {
            LOGGER.error("Failed to parse config {} due to malformed data, resetting...", this, e);
            resetFile();
        }
    }

    protected void preSerialize(JsonObject root, Map<String, String> comments) {}
    protected String serialize() {
        final JsonObject root = new JsonObject();
        final Map<String, String> comments = new LinkedHashMap<>();

        for (Field field : getClass().getDeclaredFields()) {
            Config cfg = field.getAnnotation(Config.class);
            if (cfg == null) {
                continue;
            }

            String comment = getFieldComment(field);
            if (!comment.isBlank())
            {
                comments.put(field.getName(), comment);
            }

            getJsonAtPath(root, cfg.value()).add(field.getName(), fieldToJson(field));
        }

        preSerialize(root, comments);

        String jsonStr = GSON.toJson(root);
        StringBuilder builder = new StringBuilder();
        String[] lines = jsonStr.split("\n");
        for (String line : lines) {
            // Two quotes and a colon symbol is already 3, so if less, it's likely a brace line
            if (line.trim().length() < 3) {
                builder.append(line).append("\n");
                continue;
            }
            for (Map.Entry<String, String> entry : comments.entrySet()) {
                int pos = line.trim().indexOf(entry.getKey());
                // Only correct if pos is 1 since opening quote is the 0th and the name/key is the 1st
                if (pos != 1) {
                    continue;
                }

                // Find how many tabs/spaces are behind the property so we can indent the comments appropriately
                String tabs = line.substring(0, line.indexOf("\""));
                builder.append(entry.getValue().replace(TAB_KEY, tabs)).append("\n");
                break;
            }
            builder.append(line).append("\n");
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    private JsonObject getJsonAtPath(JsonObject root, String path) {
        if (path.isBlank()) {
            return root;
        }

        String[] parts = path.split("\\.");
        JsonObject current = root;
        for (String part : parts) {
            JsonElement element = current.get(part);
            if (element == null) {
                markDirty();
                element = new JsonObject();
                current.add(part, element);
                current = element.getAsJsonObject();
                continue;
            }

            if (element.isJsonObject()) {
                current = element.getAsJsonObject();
            } else {
                markDirty();
                element = new JsonObject();
                current.add(part, element);
                LOGGER.error("Json element {} is not a JsonObject. Are you trying to add Json to a property?", part);
            }
        }
        return current;
    }

    private void jsonToField(Field field, JsonElement element) {
        field.setAccessible(true);
        try {
            field.set(this, GSON.fromJson(element, field.getType()));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            LOGGER.error("Failed to deserialize config property: {} in {}", field.getName(), this, e);
        }
    }

    private JsonElement fieldToJson(Field field) {
        field.setAccessible(true);
        try {
            return GSON.toJsonTree(field.get(this));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            LOGGER.error("Failed to serialize config property: {} in {}", field.getName(), this, e);
            return null;
        }
    }

    private String getFieldComment(Field field) {
        Comment[] commentArr = null;
        Comments comments = field.getAnnotation(Comments.class);
        if (comments != null) {
            commentArr = comments.value();
        } else {
            Comment cmt = field.getAnnotation(Comment.class);
            if (cmt != null) {
                commentArr = new Comment[]{cmt};
            }
        }

        if (commentArr == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Comment comment : commentArr) {
            // Remove new line characters from the comment itself
            String message = comment.value().replace("\n", "  ");

            builder.append(TAB_KEY);
            if (!message.isBlank()) {
                builder.append("// ").append(message);
            }
            builder.append("\n");
        }

        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    @Override
    public String toString() {
        String[] name = filePath.toString().split("config", 2);
        return getClass().getSimpleName() + "[Path=" + name[1].replace("\\", "/") +"]";
    }
}
