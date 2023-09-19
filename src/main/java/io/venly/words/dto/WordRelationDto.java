package io.venly.words.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.venly.words.converter.ToLowerCaseConverter;
import io.venly.words.entity.RelationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record WordRelationDto(
        @NotEmpty(message = "Word one is required")
        @JsonDeserialize(converter = ToLowerCaseConverter.class)
        @Pattern(regexp = "[A-Za-z\\s]+", message = "Only characters from A-Z, a-z and spaces are allowed for word one")
        String wordOne,
        @NotEmpty(message = "Word two is required")
        @JsonDeserialize(converter = ToLowerCaseConverter.class)
        @Pattern(regexp = "[A-Za-z\\s]+", message = "Only characters from A-Z, a-z and spaces are allowed for word two")
        String wordTwo,
        @NotNull(message = "Relation is required")
        RelationType relation,
        Boolean inverse

) {
}
