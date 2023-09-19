package io.venly.words.dto;

import io.venly.words.entity.RelationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record WordRelationDto(
        @NotEmpty(message = "Word one is required")
        String wordOne,
        @NotEmpty(message = "Word two is required")
        String wordTwo,
        @NotNull(message = "Relation is required")
        RelationType relation,
        Boolean inverse

) {
}
