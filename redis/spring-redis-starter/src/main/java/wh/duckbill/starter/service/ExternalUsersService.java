package wh.duckbill.starter.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ExternalUsersService {
    public String getUserName(String userId) {
        // 외부 서비스나 DB 호출
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Get Username From external service");

        if (userId.equals("A")) {
            return "Adam";
        }

        if (userId.equals("B")) {
            return "Bob";
        }

        return "";
    }

    @Cacheable(cacheNames = "userAgeCache", key = "#userId")
    public int getUserAge(String userId) {
        // 외부 서비스나 DB 호출
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Get UserAge From external service");

        if (userId.equals("A")) {
            return 28;
        }

        if (userId.equals("B")) {
            return 32;
        }

        return 0;
    }
}
