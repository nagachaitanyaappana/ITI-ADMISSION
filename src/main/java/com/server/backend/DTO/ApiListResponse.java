package com.server.backend.DTO;

import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiListResponse<T> {
    private boolean success;
    private int count;
    private List<T> data;

    public ApiListResponse(List<T> data) {
        this.success = true;
        this.data = data != null ? data : Collections.emptyList();
        this.count = this.data.size();
    }
}