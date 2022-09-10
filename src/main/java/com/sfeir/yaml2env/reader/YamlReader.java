package com.sfeir.yaml2env.reader;

import com.sfeir.yaml2env.exception.Yaml2EnvException;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class YamlReader {

    public static List<String> convertToEnvList(InputStream yamlInputStream) {
        var yamlMap = readYaml(yamlInputStream);

        return MapFlattener.flatten(yamlMap)
                .map(YamlReader::convertPropertyToEnv)
                .toList();
    }

    private static String convertPropertyToEnv(Map.Entry<String, String> entry) {
        var key = relaxedBootBinding(entry.getKey());
        var value = formatEnvValue(entry.getValue());
        return format("%s=%s", key, value);
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

    private static String formatEnvValue(@NotNull String rawValue) {
        return rawValue.contains(" ") ? format("\"%s\"", rawValue) : rawValue;
    }

    private static Map<String, Object> readYaml(@NotNull InputStream inputStream) {
        try {
            var yaml = new Yaml();
            return yaml.load(inputStream);
        } catch (Exception exception) {
            throw new Yaml2EnvException("Yaml file structure is invalid.", exception);
        }
    }

}
