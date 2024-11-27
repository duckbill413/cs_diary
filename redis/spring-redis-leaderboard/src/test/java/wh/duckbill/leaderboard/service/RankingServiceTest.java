package wh.duckbill.leaderboard.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RankingServiceTest {
    @Autowired
    private RankingService rankingService;

@Test
void inMemorySortPerformance() {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 100_000; i++) {
        int score = (int) (Math.random() * 100_000);
        list.add(score);
    }

    Instant before = Instant.now();
    list.sort(Integer::compareTo);
    Duration elapsed = Duration.between(before, Instant.now());
    System.out.println("InMemorySortPerformance: " + (elapsed.getNano() / 1000000) + "ms");
}

@Test
void insertScore() {
    for (int i = 0; i < 100_000; i++) {
        int score = (int) (Math.random() * 100_000);
        String userId = "user_" + i;
        rankingService.setUserScore(userId, score);
    }
}

    @Test
    void getRanks() {
        rankingService.getTopRank(1);

        // 1)
        Instant before = Instant.now();
        Long user100 = rankingService.getUserRanking("user_100");
        Duration elapsed = Duration.between(before, Instant.now());
        System.out.println("Redis getRank user100, " + user100 + " time: " + (elapsed.getNano() / 1000000) + "ms");

        // 2)
        before = Instant.now();
        List<String> topRanks = rankingService.getTopRank(10);
        elapsed = Duration.between(before, Instant.now());
        System.out.println("Redis getRanks Range: " + (elapsed.getNano() / 1000000) + "ms");
    }
}