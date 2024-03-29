package com.example.part1mysql.application.controller;

import com.example.part1mysql.application.usecase.CreateFollowMemberUsecase;
import com.example.part1mysql.application.usecase.GetFollowingMembersUsecase;
import com.example.part1mysql.domain.member.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-03-18
 * description   :
 **/
@Tag(name = "팔로우 정보")
@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final CreateFollowMemberUsecase createFollowMemberUsecase;
    private final GetFollowingMembersUsecase getFollowingMembersUsecase;

    @Operation(summary = "팔로우 등록")
    @PostMapping("/{fromId}/{toId}")
    public void create(@PathVariable Long fromId,
                       @PathVariable Long toId) {
        createFollowMemberUsecase.execute(fromId, toId);
    }

    // FIXME: member이 존재하는지 확인 필요
    // FIXME: follow, follower 가 동일한 값을 추가할때 에러 발생 처리 (SQLIntegrityConstraintViolationException)
    @Operation(summary = "팔로워 조회")
    @GetMapping("/members/{fromId}")
    public List<MemberDto> getFollowings(@PathVariable Long fromId) {
        return getFollowingMembersUsecase.execute(fromId);
    }
}
