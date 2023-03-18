package com.example.part1mysql.domain.follow.service;

import com.example.part1mysql.domain.follow.entity.Follow;
import com.example.part1mysql.domain.follow.repository.FollowJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-03-17
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class FollowReadService {
    private final FollowJdbcRepository followJdbcRepository;
    public List<Follow> getFollowings(Long memberId){
        return followJdbcRepository.findAllByFromMemberId(memberId);
    }
}
