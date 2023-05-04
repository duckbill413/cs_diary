package com.example.part1mysql.domain.post.repository;

import com.example.part1mysql.domain.util.PageHelper;
import com.example.part1mysql.domain.post.dto.DailyPostCount;
import com.example.part1mysql.domain.post.dto.DailyPostCountRequest;
import com.example.part1mysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/
@Repository
@RequiredArgsConstructor
public class PostRepository {
    static final String TABLE = "Post";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    final static private RowMapper<DailyPostCount> DAILY_POST_COUNT_MAPPER = (rs, rowNum) ->
            new DailyPostCount(rs.getLong("memberId"),
                    rs.getObject("createdDate", LocalDate.class),
                    rs.getLong("count"));
    final static private RowMapper<Post> POST_ROW_MAPPER = (rs, rowNum) -> Post.builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .contents(rs.getString("contents"))
            .createdDate(rs.getObject("createdDate", LocalDate.class))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build();


    public Post save(Post post) {
        if (post.getId() == null)
            return insert(post);
//        return update(post);
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdAt(post.getCreatedAt())
                .createdDate(post.getCreatedDate())
                .build();
    }

    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        var sql = String.format("""
                SELECT createdDate, memberId, count(id)
                FROM %s
                WHERE memberId = :memberId and createdDate between :firstDate and :lastDate
                GROUP BY createdDate memberId
                """, TABLE);
        var params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_MAPPER);
    }

    public Page<Post> findAllMemberId(Long memberId, Pageable pageable) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY %s
                LIMIT :size
                OFFSET :offset""", TABLE, PageHelper.orderBy(pageable.getSort()));
        List<Post> result = namedParameterJdbcTemplate.query(sql, params, POST_ROW_MAPPER);

        return new PageImpl<>(result, pageable, getCount(memberId));
    }
    private Long getCount(Long memberId){
        var sql = String.format("""
                SELECT count(*)
                FROM POST
                WHERE memberId = :memberId
                """, TABLE);
        var param = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        return namedParameterJdbcTemplate.queryForObject(sql, param, Long.class);
    }
    public void bulkInsert(List<Post> posts) {
        var sql = String.format("""
                INSERT INTO `%s` (memberId, contents, createdDate, createdAt)
                VALUES (:memberId, :contents, :createdDate, :createdAt)
                """, TABLE);

        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        // batchUpdate를 사용하여 List를 binding 시킬 수 있다.
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }
}
