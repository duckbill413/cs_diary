package com.example.part1mysql.domain.follow.service;

import com.example.part1mysql.domain.follow.entity.Follow;
import com.example.part1mysql.domain.follow.repository.FollowJdbcRepository;
import com.example.part1mysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * author        : duckbill413
 * date          : 2023-03-17
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class FollowWriteService {
    private final FollowJdbcRepository followJdbcRepository;
    public void create(MemberDto fromMember, MemberDto toMember) {
        /*
            from, to 회원 정보를 받아서 저장
            from <-> to validate
         */
        Assert.isTrue(!fromMember.equals(toMember), "From, To 회원이 동일합니다.");

        Follow follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();

        followJdbcRepository.save(follow);
    }
}
