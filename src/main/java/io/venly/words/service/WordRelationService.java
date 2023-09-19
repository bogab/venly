package io.venly.words.service;

import io.venly.words.dto.WordRelationDto;
import io.venly.words.entity.RelationType;
import io.venly.words.entity.WordRelation;
import io.venly.words.repository.WordRelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WordRelationService {

    private final WordRelationRepository wordRelationRepository;

    public WordRelation createWordRelation(WordRelationDto request) {

        log.info("Create a relation between: {} <-> {} of type {}", request.wordOne(), request.wordTwo(), request.relation());

        var wordRelation = WordRelation.builder()
                .wordOne(request.wordOne())
                .wordTwo(request.wordTwo())
                .relation(request.relation())
                .relation(request.relation())
                .build();

        return wordRelationRepository.save(wordRelation);
    }

    public List<WordRelationDto> getWordRelations(Optional<RelationType> relation, Optional<Boolean> inverse) {
        log.info("Fetching relations");

        var entities = relation.map(wordRelationRepository::findAllByRelation)
                .orElseGet(wordRelationRepository::findAll);

        var includeInverse = inverse.orElse(false);

        var length = includeInverse ? 2 * entities.size() : entities.size();

        var results = new ArrayList<WordRelationDto>(length);

        var i = 0;
        for (WordRelation e : entities) {
            results.add(i++, new WordRelationDto(e.getWordOne(), e.getWordTwo(), e.getRelation(), false));
            if (includeInverse) {
                results.add(i++, new WordRelationDto(e.getWordTwo(), e.getWordOne(), e.getRelation(), true));
            }
        }

        return results;
    }
}
