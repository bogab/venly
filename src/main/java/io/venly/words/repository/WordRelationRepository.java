package io.venly.words.repository;

import io.venly.words.entity.WordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRelationRepository extends JpaRepository<WordRelation, Long> {
}
