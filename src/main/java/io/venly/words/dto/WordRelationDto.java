package io.venly.words.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.venly.words.converter.ToLowerCaseConverter;
import io.venly.words.entity.RelationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record WordRelationDto(
        @NotEmpty(message = "Word one is required")
        @JsonDeserialize(converter = ToLowerCaseConverter.class)
        String wordOne,
        @NotEmpty(message = "Word two is required")
        @JsonDeserialize(converter = ToLowerCaseConverter.class)
        String wordTwo,
        @NotNull(message = "Relation is required")
        RelationType relation,
        Boolean inverse

) {
}
