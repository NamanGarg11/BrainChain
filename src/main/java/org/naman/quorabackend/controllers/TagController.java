package org.naman.quorabackend.controllers;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.models.Tags;
import org.naman.quorabackend.services.ITagService;
import org.naman.quorabackend.services.TagService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final ITagService tagService;

    /**
     * Get trending tags (sorted by usageCount desc).
     */
    @GetMapping("/trending")
    public Flux<Tags> getTrendingTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return tagService.getTrendingTags(page, size);
    }

    /**
     * Create or fetch tags from a string (e.g. "java,spring,mongo").
     */
    @PostMapping("/create")
    public Mono<List<String>> createTagsFromString(@RequestBody String tagsString) {
        return tagService.getOrCreateAllFromString(tagsString);
    }

    /**
     * Increment usage count for given tag names.
     */
    @PutMapping("/increment")
    public Mono<Void> incrementTags(@RequestBody Collection<String> tagNames) {
        return tagService.incrementUsageForNames(tagNames);
    }

    /**
     * Decrement usage count for given tag names.
     */
    @PutMapping("/decrement")
    public Mono<Void> decrementTags(@RequestBody Collection<String> tagNames) {
        return tagService.decrementUsageForNames(tagNames);
    }
}
