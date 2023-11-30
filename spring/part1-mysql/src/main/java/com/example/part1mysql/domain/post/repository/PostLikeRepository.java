package com.example.part1mysql.domain.post.repository;

import com.example.part1mysql.domain.post.entity.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class PostLikeRepository {
    private static final String TABLE = "PostLike";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    final static private RowMapper<PostLike> ROW_MAPPER = (rs, rowNum) -> PostLike.builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .postId(rs.getLong("postId"))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build();

    public PostLike save(PostLike postLike) {
        if (postLike.getId() == null) {
            return insert(postLike);
        }

        throw new UnsupportedOperationException("Timeline은 갱신을 지원하지 않습니다.");
    }

    private PostLike insert(PostLike postLike) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(postLike);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return PostLike.builder()
                .id(id)
                .memberId(postLike.getMemberId())
                .postId(postLike.getPostId())
                .createdAt(postLike.getCreatedAt())
                .build();
    }

    public Long count(Long postId) {
        var sql = String.format("""
                SELECT count(id)
                FROM %s
                WHERE postId = :postId
                """, TABLE);
        var param = new MapSqlParameterSource()
                .addValue("postId", postId);

        return namedParameterJdbcTemplate.queryForObject(sql, param, Long.class);
    }
}
