package com.example.part1mysql.domain.member.repository;

import com.example.part1mysql.domain.member.entity.Member;
import com.example.part1mysql.domain.member.entity.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-02-23
 * description   :
 **/
@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryJdbcRepository {
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String TABLE = "MemberNicknameHistory";

    static final RowMapper<MemberNicknameHistory> rowMapper = (rs, rowNum) -> MemberNicknameHistory.builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .nickname(rs.getString("nickname"))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build();
    public List<MemberNicknameHistory> findAllByMemberId(Long memberId){
        var sql = String.format("select * from %s where memberId = :memberId", TABLE);
        // MapSqlParameterSource 방식의 parameter 추가
        var params = new MapSqlParameterSource().addValue("memberId", memberId);
        // RowMapper을 통해서 DAO 변환
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }
    public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory){
        /*
            member id를 보고 갱신 또는 삽입을 정함
            반환은 id를 담아서 반환한다.
         */
        if (memberNicknameHistory.getId() == null)
            return insert(memberNicknameHistory);
        throw new UnsupportedOperationException();
    }

    private MemberNicknameHistory insert(MemberNicknameHistory memberNicknameHistory){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("MemberNicknameHistory")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(memberNicknameHistory);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return MemberNicknameHistory.builder()
                .id(id)
                .memberId(memberNicknameHistory.getMemberId())
                .nickname(memberNicknameHistory.getNickname())
                .createdAt(memberNicknameHistory.getCreatedAt())
                .build();
    }

    private Member update(Member member){
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id",
                TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, params);
        return member;
    }
}
