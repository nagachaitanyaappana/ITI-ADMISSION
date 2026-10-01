package com.server.backend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Payload posted by {@code /AdmissionPhase} when the operator enters a merit rank
 * ({@code handleRankSubmit()} in the page). The page sends {@code {"rank": "12"}}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RankLookupRequest(String rank) {
}
