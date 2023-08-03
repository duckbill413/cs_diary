package com.example.part1mysql.application.usecase;

import com.example.part1mysql.domain.follow.entity.Follow;
import com.example.part1mysql.domain.follow.service.FollowReadService;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.service.PostReadService;
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

    public static void main(String[] args) {
    }
}
