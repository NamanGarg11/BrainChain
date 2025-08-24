package org.naman.quorabackend.utils;

import java.util.*;
import java.util.stream.Collectors;

public final class TagUtils {
    private TagUtils() {}

    // Normalize a single tag: trim, toLowerCase
    public static String normalize(String raw) {
        return raw == null ? "" : raw.trim().toLowerCase();
    }

    // Parse a comma or whitespace separated string into unique, normalized tags preserving order.
    public static List<String> parseTags(String tagsString) {
        if (tagsString == null || tagsString.isBlank()) {
            return List.of();
        }
        String[] parts = tagsString.split("[,\\s]+");
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (String p : parts) {
            String n = normalize(p);
            if (!n.isEmpty()) set.add(n);
        }
        return new ArrayList<>(set);
    }

    // Normalize a collection to a unique set of tag names (lowercase, trimmed).
    public static Set<String> normalizeUnique(Collection<String> rawNames) {
        if (rawNames == null) return Set.of();
        return rawNames.stream()
                .map(TagUtils::normalize)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}