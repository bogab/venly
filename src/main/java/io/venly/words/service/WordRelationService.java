package io.venly.words.service;

import io.venly.words.dto.WordRelationDto;
import io.venly.words.entity.RelationType;
import io.venly.words.entity.WordRelation;
import io.venly.words.exception.PathNotFoundException;
import io.venly.words.repository.WordRelationRepository;
import io.venly.words.exception.RelationAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                .build();

        try {
           return wordRelationRepository.save(wordRelation);
        } catch (DataIntegrityViolationException e) {
            throw new RelationAlreadyExistsException(request.wordOne(), request.wordTwo());
        }
    }

    public List<WordRelationDto> getWordRelations(Optional<RelationType> relation, Optional<Boolean> inverse) {
        log.info("Fetching relations");

        var entities = relation.map(wordRelationRepository::findAllByRelation)
                .orElseGet(wordRelationRepository::findAll);

        var includeInverse = inverse.orElse(false);

        var length = includeInverse ? 2 * entities.size() : entities.size();

        var results = new ArrayList<WordRelationDto>(length);

        for (WordRelation e : entities) {
            results.add(new WordRelationDto(e.getWordOne(), e.getWordTwo(), e.getRelation(), false));
            if (includeInverse) {
                results.add(new WordRelationDto(e.getWordTwo(), e.getWordOne(), e.getRelation(), true));
            }
        }
        return results;
    }

    @Transactional(readOnly = true)
    public List<String> searchPath(String source, String target) {
        log.info("Searching for a path between: {} <-> {}", source, target);
        var pathFound = false;
        var visited = new HashSet<String>();
        var queue = new LinkedList<String>();
        var parentNodes = new HashMap<String, String>();

        visited.add(source);
        queue.add(source);

        while (!queue.isEmpty() && !pathFound) {
            var parent = queue.poll();
            var children = wordRelationRepository.findAllByWordOneOrWordTwo(parent, parent).stream()
                    .flatMap(r -> Stream.of(r.getWordOne(), r.getWordTwo()))
                    .filter(w -> !visited.contains(w))
                    .collect(Collectors.toSet());
            for (String word : children) {
                if (visited.add(word)) {
                    queue.add(word);
                    parentNodes.put(word, parent);
                }
                if (target.equals(word)) {
                    pathFound = true;
                    break;
                }
            }
        }
        if (!pathFound) {
            log.info("A path between {} <-> {} could not be found", source, target);
            throw new PathNotFoundException(source, target);
        }
        List<String> shortestPath = new ArrayList<>();
        var word = target;
        while (word != null) {
            shortestPath.add(word);
            word = parentNodes.get(word);
        }
        Collections.reverse(shortestPath);

        log.info("The shortest path between {} <-> {} is: {}", source, target, shortestPath);

        return shortestPath;
    }
}
