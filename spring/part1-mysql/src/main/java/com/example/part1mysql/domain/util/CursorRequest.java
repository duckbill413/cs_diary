package com.example.part1mysql.domain.util;

/**
 * author        : duckbill413
 * date          : 2023-04-30
 * description   :
 **/

public record CursorRequest(
        Long key,
        int size
) {
    public static final Long NONE_KEY = -1L; // End Data

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }

    public boolean hasKey() {
        return key != null;
    }
}
