package com.example.part1mysql.domain.util;

import java.util.List;

public record PageCursor<T> (
        CursorRequest nextCursorRequest,
        List<T> body
) {
}
