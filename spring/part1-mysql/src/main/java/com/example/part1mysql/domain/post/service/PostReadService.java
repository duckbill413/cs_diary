package com.example.part1mysql.domain.post.service;

import com.example.part1mysql.domain.post.dto.DailyPostCount;
import com.example.part1mysql.domain.post.dto.DailyPostCountRequest;
import com.example.part1mysql.domain.post.dto.PostDto;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.repository.PostLikeRepository;
import com.example.part1mysql.domain.post.repository.PostRepository;
import com.example.part1mysql.domain.util.CursorRequest;
import com.example.part1mysql.domain.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class PostReadService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByInId(ids);
    }

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
        반환 값 -> 리스트 [작성일자, 작성회원, 작성 게시물 수]
        select *
        from Post
        where memberId = :memberId and createdDate between firstDate and lastDate
        group by createdDate memberId
         */
        return postRepository.groupByCreatedDate(request);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId, false).orElseThrow();
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllMemberId(memberId, pageable).map(this::toDto);
    }

    private PostDto toDto(Post post) {
        // FIXME: PostDto를 생성할 때마다 조회가 발생하게 된다.
        return new PostDto(
                post.getId(),
                post.getContents(),
                post.getCreatedAt(),
                postLikeRepository.count(post.getId())
        );
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);
        long nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = findAllBy(memberIds, cursorRequest);
        long nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        } else {
            return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        }
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        } else {
            return postRepository.findAllByInMemberIdAndOrderByIdDesc(memberIds, cursorRequest.size());
        }
    }

    private static long getNextKey(List<Post> posts) {
        return posts.stream().mapToLong(Post::getId).min().orElse(CursorRequest.NONE_KEY);
    }
}
