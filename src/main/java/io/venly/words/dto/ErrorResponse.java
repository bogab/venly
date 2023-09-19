package io.venly.words.dto;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, HttpStatus status, String message) {

}
