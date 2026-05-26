package dev.brodino.staminup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Path configPath;
    private Config.Type data;

    public Config(String modId, Logger logger) {
        Path dataDirectory = Path.of("config");

        try {
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }
            this.configPath = dataDirectory.resolve(modId + ".json");
            this.load();
        } catch (IOException e) {
            logger.error("Failed to load {}.json", modId);
        }
    }

    private void load() throws IOException {
        if (!Files.exists(this.configPath)) {
            this.data = this.getDefaults();
            this.save();
            return;
        }

        try (Reader reader = Files.newBufferedReader(this.configPath)) {
            this.data = GSON.fromJson(reader, Config.Type.class);
            if (data == null) {
                this.data = this.getDefaults();
                this.save();
            } else {
                this.data.validate();
            }
        }
    }

    public boolean reload() {
        try {
            this.load();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    private void save() throws IOException {
        try (Writer writer = Files.newBufferedWriter(this.configPath)) {
            GSON.toJson(this.data, writer);
        }
    }

    private Config.Type getDefaults() { return new Config.Type(); }

    public Config.Type getData() { return this.data; }

    public static class Type {
        private float maxStamina = 100;
        private float staminaRegen = 2;
        private float jumpCost = 10;
        @SuppressWarnings({ "FieldMayBeFinal", "FieldCanBeLocal" }) // I want to kill myself
        private boolean movementCheck = false;

        public float getMaxStamina() { return this.maxStamina; }
        public float getStaminaRegen() { return this.staminaRegen; }
        public float getJumpCost() { return this.jumpCost; }
        public float getMovementCheck() { return this.movementCheck ? 1f : 0f; }

        public void validate() {
            if (this.maxStamina <= 0) { this.maxStamina = 100; }
            if (this.staminaRegen <= 0) { this.staminaRegen = 2; }
            if (this.jumpCost <= 0) { this.jumpCost = 10; }
            if (this.jumpCost > this.maxStamina) { this.jumpCost = this.maxStamina; }
        }
    }
}
