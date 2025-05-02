package com.prabhu.myapp.config.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ConfigLoader {
    private static final String FRAMEWORK_CONFIG_PATH = "application.yml";
    private static final String ENV_CONFIG_FOLDER = "environment/";
    private final FrameworkConfig frameworkConfig;
    private final EnvironmentConfig environmentConfig;
    private static final Logger logger = LoggerHelper.getLogger(ConfigLoader.class);

    public ConfigLoader() {
        this.frameworkConfig = loadFrameworkConfig();
        this.environmentConfig = loadMergedEnvironmentConfig(frameworkConfig.getApplicationConfig().getEnvironment());
    }

    private FrameworkConfig loadFrameworkConfig() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FRAMEWORK_CONFIG_PATH)) {
            if (inputStream == null) {
                throw new RuntimeException("Framework config not found: " + FRAMEWORK_CONFIG_PATH);
            }
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            FrameworkConfig config = mapper.readValue(inputStream, FrameworkConfig.class);
            logger.info("✅ Loaded framework config from {}", FRAMEWORK_CONFIG_PATH);
            return config;
        } catch (IOException e) {
            throw new RuntimeException("Error loading framework config", e);
        }
    }

    private EnvironmentConfig loadMergedEnvironmentConfig(String environment) {
        String envFilePath = ENV_CONFIG_FOLDER + environment.toLowerCase() + ".yml";
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (
                InputStream baseStream = getClass().getClassLoader().getResourceAsStream(FRAMEWORK_CONFIG_PATH);
                InputStream envStream = getClass().getClassLoader().getResourceAsStream(envFilePath)
        ) {
            if (baseStream == null) {
                throw new RuntimeException("Missing application.yml");
            }
            if (envStream == null) {
                throw new RuntimeException("Missing environment config: " + envFilePath);
            }

            // Load YAMLs into JsonNode
            JsonNode baseNode = mapper.readTree(baseStream);
            JsonNode envNode = mapper.readTree(envStream);

            // Perform deep merge
            JsonNode mergedNode = mergeJsonNodes(baseNode, envNode);

            // Convert back to EnvironmentConfig
            EnvironmentConfig mergedConfig = mapper.treeToValue(mergedNode, EnvironmentConfig.class);

            logger.info("✅ Loaded and deeply merged environment config for {}", environment);

            // Write merged config to file for debugging
            if (frameworkConfig.getDebug() != null && frameworkConfig.getDebug().isWriteMergedConfig()) {
                writeMergedYamlToFile(mergedConfig, environment);
            }
            return mergedConfig;

        } catch (IOException e) {
            throw new RuntimeException("Error loading environment config", e);
        }
    }

    private JsonNode mergeJsonNodes(JsonNode baseNode, JsonNode overrideNode) {
        if (baseNode == null || baseNode.isNull()) return overrideNode;
        if (overrideNode == null || overrideNode.isNull()) return baseNode;

        if (baseNode.isObject() && overrideNode.isObject()) {
            ObjectNode merged = ((ObjectNode) baseNode).deepCopy();
            Iterator<String> fieldNames = overrideNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode baseField = baseNode.get(fieldName);
                JsonNode overrideField = overrideNode.get(fieldName);
                JsonNode mergedField = mergeJsonNodes(baseField, overrideField);
                merged.set(fieldName, mergedField);
            }
            return merged;
        }

        return overrideNode;
    }

    public FrameworkConfig getFrameworkConfig() {
        return frameworkConfig;
    }

    public EnvironmentConfig getEnvironmentConfig() {
        return environmentConfig;
    }

    private void writeMergedYamlToFile(EnvironmentConfig config, String environment) {
        ObjectMapper yamlWriter = new ObjectMapper(new YAMLFactory());
        try {
            String fileName = "target/merged-config-" + environment + ".yml";
            yamlWriter.writeValue(new java.io.File(fileName), config);
            logger.info("📝 Merged config written to {}", fileName);
        } catch (IOException e) {
            logger.warn("⚠️ Failed to write merged config file", e);
        }
    }
}