package com.example.part1mysql.domain.member.repository;

import com.example.part1mysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * author        : duckbill413
 * date          : 2023-02-23
 * description   :
 **/
@Repository
@RequiredArgsConstructor
public class MemberJdbcRepository {
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String TABLE = "member";
    public Optional<Member> findById(Long id){
        var sql = String.format("select * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource()
                .addValue("id", id);
        RowMapper<Member> rowMapper = (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .nickname(rs.getString("nickname"))
                .birthday(rs.getObject("birthday", LocalDate.class))
                .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                .build();

        Member result = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);
        return Optional.ofNullable(result);
    }
    public Member save(Member member){
        /*
            member id를 보고 갱신 또는 삽입을 정함
            반환은 id를 담아서 반환한다.
         */
        if (member.getId() == null)
            return insert(member);

        return update(member);
    }

    private Member insert(Member member){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .nickname(member.getNickname())
                .createdAt(member.getCreatedAt())
                .build();
    }
    private Member update(Member member){
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, params);
        return member;
    }
}
