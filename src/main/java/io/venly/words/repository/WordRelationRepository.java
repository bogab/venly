package io.venly.words.repository;

import io.venly.words.entity.RelationType;
import io.venly.words.entity.WordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRelationRepository extends JpaRepository<WordRelation, Long> {

    List<WordRelation> findAllByRelation(RelationType relation);

    List<WordRelation> findAllByWordOneOrWordTwo(String wordOne, String wordTwo);

}
