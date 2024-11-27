package wh.duckbill.leaderboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.leaderboard.service.RankingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final RankingService rankingService;

    @PostMapping("/setScore")
    public Boolean setScore(
            @RequestParam String userId,
            @RequestParam int score
    ) {
        return rankingService.setUserScore(userId, score);
    }

    @GetMapping("/getRank")
    public Long getRank(
            @RequestParam String userId
    ) {
        return rankingService.getUserRanking(userId);
    }

    @GetMapping("/getTopRanks")
    public List<String> getTopRanks(
            @RequestParam(name = "limit", required = false, defaultValue = "3") int limit
    ) {
        return rankingService.getTopRank(limit);
    }
}
