package com.sfeir.yaml2env.reader;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.String.format;

public class MapFlattener {

    public static Stream<Map.Entry<String, String>> flatten(Map<String, Object> map) {
        return flatten("", map);
    }

    private static Stream<Map.Entry<String, String>> flatten(String prefix, Map<String, Object> map) {
        return map.entrySet()
                .stream()
                .flatMap(entry -> extractValue(prefix, entry));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Stream<Map.Entry<String, String>> extractValue(String prefix, Map.Entry<String, Object> entry) {
        if (entry.getValue() instanceof Map map) {
            return flatten(entry.getKey(), map);
        } else if (entry.getValue() instanceof List list){
            var collectionMap = new HashMap<String, Object>();
            for (int i = 0; i < list.size(); i++) {
                collectionMap.put(format("%s.%s", entry.getKey(), i), list.get(i));
            }
            return flatten(prefix, collectionMap);
        }
        var key = prefix.isBlank() ? entry.getKey() : format("%s.%s", prefix, entry.getKey());
        return Stream.of(new AbstractMap.SimpleEntry<>(key, entry.getValue().toString()));
    }
}
