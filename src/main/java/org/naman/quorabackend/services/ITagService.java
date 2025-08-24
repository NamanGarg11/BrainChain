package org.naman.quorabackend.services;

import org.naman.quorabackend.models.Tags;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

public interface ITagService {

    /**
     * Ensures a tag exists; creates it if not found. Returns the tag document.
     */
    Mono<Tags> getOrCreateTags(String rawTagName);

    /**
     * Parses a tag string (e.g., "java, spring, mongo") and ensures each tag exists.
     * Returns the normalized list of tag names in insertion order (unique).
     */
    Mono<List<String>> getOrCreateAllFromString(String tagsString);

    /**
     * Increments usage count for each tag name given (creating any that don’t exist yet).
     */
    Mono<Void> incrementUsageForNames(Collection<String> rawNames);

    /**
     * Decrements usage count for each tag name given (no lower than zero).
     */
    Mono<Void> decrementUsageForNames(Collection<String> rawNames);

    /**
     * Returns trending tags sorted by usageCount desc with pagination.
     */
    Flux<Tags> getTrendingTags(int page, int size);
}
