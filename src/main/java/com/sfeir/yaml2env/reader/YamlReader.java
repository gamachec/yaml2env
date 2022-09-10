package com.sfeir.yaml2env.reader;

import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class YamlReader {

    public static List<String> toEnv(InputStream yamlInputStream) {
        var yamlMap = readYaml(yamlInputStream);

        return MapFlattener.flatten(yamlMap)
                .map(entry -> format("%s=%s", relaxedBootBinding(entry.getKey()), formatEnvValue(entry.getValue())))
                .toList();
    }

    /**
     * Use spring boot relaxed binding for formatting key.
     * Replace dots (.) with underscores (_).
     * Remove any dashes (-).
     * Convert to uppercase.
     *
     * @param rawKey raw property key
     * @return environment variable compliant key
     */
    private static String relaxedBootBinding(@NotNull String rawKey) {
        return rawKey.toUpperCase()
                .replace("-", "")
                .replace(".", "_");
    }

    private static String formatEnvValue(String rawValue) {
        return rawValue.contains(" ") ? format("\"%s\"", rawValue) : rawValue;
    }

    private static Map<String, Object> readYaml(InputStream inputStream) {
        var yaml = new Yaml();
        return yaml.load(inputStream);
    }

}
