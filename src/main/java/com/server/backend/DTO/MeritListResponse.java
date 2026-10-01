package com.server.backend.DTO;
import java.util.List;

public record MeritListResponse(
    boolean success,
    String heading,
    List<MeritListRow> data,
    String error
) {
    public MeritListResponse(boolean success, String heading, List<MeritListRow> data) {
        this(success, heading, data, null);
    }

    public MeritListResponse(boolean success, String error) {
        this(success, null, null, error);
    }
}
