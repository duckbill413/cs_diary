package com.example.part1mysql.application.usecase;

import com.example.part1mysql.domain.follow.entity.Follow;
import com.example.part1mysql.domain.follow.service.FollowReadService;
import com.example.part1mysql.domain.member.dto.MemberDto;
import com.example.part1mysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author        : duckbill413
 * date          : 2023-03-18
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class GetFollowingMembersUsecase {
    private final FollowReadService followReadService;
    private final MemberReadService memberReadService;
    public List<MemberDto> execute(Long memberId){
        /*
        1. fromMemberId = memberId -> Follow list
        2. 순번을 순회하면서 회원정보를 찾으면 된다.
         */
        List<Follow> follows = followReadService.getFollowings(memberId);
        var followingMemberIds = follows.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMemberIds);
    }
}
