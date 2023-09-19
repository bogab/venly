package io.venly.words.controller;

import io.venly.words.dto.WordRelationDto;
import io.venly.words.service.WordRelationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/word/relations")
public class WordRelationController {

    private final WordRelationService wordRelationService;

    @PostMapping
    public Long createWordRelation(@RequestBody @Valid WordRelationDto request) {
        var wordRelation = wordRelationService.createWordRelation(request);
        return wordRelation.getId();
    }

    @GetMapping
    public List<WordRelationDto> getWordRelations() {
        List<WordRelationDto> wordRelations = wordRelationService.getWordRelations();
        return wordRelations;
    }

}
