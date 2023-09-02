package com.example.part1mysql.application.usecase;

import com.example.part1mysql.domain.follow.entity.Follow;
import com.example.part1mysql.domain.follow.service.FollowReadService;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.entity.Timeline;
import com.example.part1mysql.domain.post.service.PostReadService;
import com.example.part1mysql.domain.post.service.TimelineReadService;
import com.example.part1mysql.domain.util.CursorRequest;
import com.example.part1mysql.domain.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUsecase {
    private final PostReadService postReadService;
    private final FollowReadService followReadService;
    private final TimelineReadService timelineReadService;

    /**
     * 1. memberId -> follow 조회
     * 2. 1번 결과로 게시물 조회
     *
     * @param memberId      member Id
     * @param cursorRequest cursor based pagination
     * @return Post List
     */
    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    /**
     * 1. Timeline 조회
     * 2. 1번에 해당하는 게시물을 조회한다.
     *
     * @param memberId
     * @param cursorRequest
     * @return
     */
    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        var pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        List<Long> postIds = pagedTimelines.body().stream().map(Timeline::getPostId).toList();
        var posts = postReadService.getPosts(postIds);

        return new PageCursor<>(pagedTimelines.nextCursorRequest(), posts);
    }
}
