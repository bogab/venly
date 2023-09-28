package io.venly.words.controller;

import io.venly.words.dto.WordRelationDto;
import io.venly.words.entity.RelationType;
import io.venly.words.service.WordRelationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/word/relations")
public class WordRelationController {

    private final WordRelationService wordRelationService;

    @PostMapping
    public ResponseEntity<Long> createWordRelation(@RequestBody @Valid WordRelationDto request) {
        var wordRelation = wordRelationService.createWordRelation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(wordRelation.getId());
    }

    @GetMapping
    public ResponseEntity<List<WordRelationDto>> getWordRelations(@RequestParam Optional<RelationType> relation, @RequestParam Optional<Boolean> inverse) {
        return ResponseEntity.ok(wordRelationService.getWordRelations(relation, inverse));
    }

    @GetMapping(value = "/path")
    public ResponseEntity<List<String>> pathSearch(@RequestParam String source, @RequestParam String target) {
        return ResponseEntity.ok(wordRelationService.searchPath(source, target));
    }

}
