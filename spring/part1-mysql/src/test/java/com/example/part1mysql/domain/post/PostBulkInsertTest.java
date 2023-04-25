package com.example.part1mysql.domain.post;

import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.repository.PostRepository;
import com.example.part1mysql.util.PostFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

/**
 * author        : duckbill413
 * date          : 2023-04-21
 * description   :
 **/
@SpringBootTest
public class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    @DisplayName("bulk insert test")
    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 2, 1)
        );
        var stopWatch = new StopWatch();
        stopWatch.start();

        List<Post> posts = IntStream.range(0, 1000000)
                .parallel()
                .mapToObj(value -> easyRandom.nextObject(Post.class))
                .toList();


        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();
        queryStopWatch.start();
        postRepository.bulkInsert(posts);
        queryStopWatch.stop();
        System.out.println("DB 생성 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }
}
