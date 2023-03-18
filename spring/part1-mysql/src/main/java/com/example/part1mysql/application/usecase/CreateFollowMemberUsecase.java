package com.example.part1mysql.application.usecase;

import com.example.part1mysql.domain.follow.service.FollowWriteService;
import com.example.part1mysql.domain.member.entity.Member;
import com.example.part1mysql.domain.member.service.MemberReadService;
import com.example.part1mysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * author        : duckbill413
 * date          : 2023-03-17
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsecase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;
    public void execute(Long fromMemberId, Long toMemberId){
        /*
        1. 입력받은 memberId로 회원 조회
        2. FollowWriteService.create()
         */
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember, toMember);
    }
}
