package com.example.part1mysql.application.usecase;

import com.example.part1mysql.domain.follow.entity.Follow;
import com.example.part1mysql.domain.follow.service.FollowReadService;
import com.example.part1mysql.domain.post.dto.PostCommand;
import com.example.part1mysql.domain.post.service.PostWriteService;
import com.example.part1mysql.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {
    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final  TimelineWriteService timelineWriteService;

    public Long execute(PostCommand postCommand) {
        var postId = postWriteService.create(postCommand);

        List<Long> followerMemberIds = followReadService.getFollowers(postCommand.memberId())
                .stream().map(Follow::getFromMemberId)
                .toList();

        timelineWriteService.deliveryToTimeline(postId, followerMemberIds);
        return postId;
    }
}
