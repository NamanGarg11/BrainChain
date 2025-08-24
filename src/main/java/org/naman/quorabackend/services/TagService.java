package org.naman.quorabackend.services;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.models.Tags;
import org.naman.quorabackend.repositories.TagRepository;
import org.naman.quorabackend.utils.TagUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    // Ensures a tag exists; creates it if not found. Returns the tag document.
    public Mono<Tags> getOrCreateTags(String rawTagName) {
        String tagName = TagUtils.normalize(rawTagName);
        if (tagName.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Tag name cannot be empty"));
        }
        return tagRepository.findByTagName(tagName)
                .switchIfEmpty(
                        tagRepository.save(
                                Tags.builder()
                                        .TagName(tagName)
                                        .usageCount(0)
                                        .build()
                        )
                );
    }

    // Parses a tag string (e.g., "java, spring, mongo") and ensures each tag exists.
    // Returns the normalized list of tag names in insertion order (unique).
    public Mono<List<String>> getOrCreateAllFromString(String tagsString) {
        List<String> names = TagUtils.parseTags(tagsString);
        return Flux.fromIterable(names)
                .concatMap(this::getOrCreateTags)
                .map(Tags::getTagName)
                .collectList();
    }

    // Increments usage count for each tag name given (creating any that don’t exist yet).
    public Mono<Void> incrementUsageForNames(Collection<String> rawNames) {
        Set<String> names = TagUtils.normalizeUnique(rawNames);
        return Flux.fromIterable(names)
                .concatMap(name ->
                        getOrCreateTags(name)
                                .flatMap(tag -> {
                                    tag.setUsageCount(tag.getUsageCount() + 1);
                                    return tagRepository.save(tag);
                                })
                )
                .then();
    }

    // Decrements usage count for each tag name given (no lower than zero).
    public Mono<Void> decrementUsageForNames(Collection<String> rawNames) {
        Set<String> names = TagUtils.normalizeUnique(rawNames);
        return Flux.fromIterable(names)
                .concatMap(name ->
                        tagRepository.findByTagName(name)
                                .flatMap(tag -> {
                                    int next = Math.max(0, tag.getUsageCount() - 1);
                                    tag.setUsageCount(next);
                                    return tagRepository.save(tag);
                                })
                )
                .then();
    }

    // Returns trending tags sorted by usageCount desc with pagination.
    public Flux<Tags> getTrendingTags(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tagRepository.findAllByOrderByUsageCountDesc(pageable);
    }
}
