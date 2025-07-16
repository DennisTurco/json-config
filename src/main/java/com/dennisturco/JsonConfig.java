package com.dennisturco;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * A utility class to load and flatten JSON configuration files into a flat key-value map.
 *
 * This class reads a JSON configuration file and flattens nested structures into a single-level
 * HashMap with concatenated keys (e.g., "MenuItems_BugReport"). It supports reloading and accessing
 * individual configuration values by key.
 */
public class JsonConfig {

    /** Stores the flattened key-value configuration map. */
    private HashMap<String, Object> configs;

    /** Path to the JSON configuration file. */
    private final String filePath;

    /**
     * Constructs a new JsonConfig with the specified file path.
     *
     * @param filePath The path to the JSON configuration file.
     * @throws IOException if the file cannot be read.
     */
    public JsonConfig(String filePath) throws IOException {
        this.filePath = filePath;
        loadConfigsFromJson();
    }

    /**
     * Returns a configuration value by key.
     *
     * @param key The configuration key.
     * @return The associated value, or null if the key doesn't exist.
     */
    public Object getConfigByKey(String key) {
        return configs.get(key);
    }

    /**
     * Returns the full map of flattened configuration values.
     *
     * @return A HashMap containing all configuration keys and values.
     */
    public HashMap<String, Object> getConfigs() {
        return configs;
    }

    /**
     * Prints all configuration key-value pairs to the standard output.
     */
    public void printAllConfigs() {
        for (Map.Entry<String, Object> entry : configs.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Reloads the configuration file and updates the internal config map.
     *
     * @throws IOException if the file cannot be read.
     */
    public void reloadConfigs() throws IOException {
        loadConfigsFromJson();
    }

    /**
     * Loads and flattens the JSON configuration from the file.
     *
     * This method uses Jackson to parse the file and populates the {@code configs} map
     * with flattened key-value pairs.
     *
     * @throws IOException if the file cannot be read.
     */
    private void loadConfigsFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(filePath));
        configs = new HashMap<>();
        flattenJson("", root, configs);
    }

    /**
     * Recursively flattens a JSON structure into a single-level map.
     *
     * @param prefix The key prefix used to build flattened keys.
     * @param node The current JSON node being processed.
     * @param result The map where flattened key-value pairs are stored.
     */
    private static void flattenJson(String prefix, JsonNode node, Map<String, Object> result) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.properties().iterator();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = prefix.isEmpty() ? field.getKey() : prefix + "_" + field.getKey();
                flattenJson(key, field.getValue(), result);
            }
        } else {
            result.put(prefix, node.isBoolean() ? node.booleanValue() :
                    node.isTextual() ? node.textValue() :
                    node.isNumber() ? node.numberValue() : node.toString());
        }
    }
}
