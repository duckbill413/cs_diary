package wh.duckbill.starter.service;

import org.springframework.stereotype.Service;

@Service
public class ExternalUsersService {
    public String getUserName(String userId) {
        // 외부 서비스나 DB 호출
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (userId.equals("A")) {
            return "Adam";
        }

        if (userId.equals("B")) {
            return "Bob";
        }

        return "";
    }
    public int getUserAge(String userId) {
        // 외부 서비스나 DB 호출
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (userId.equals("A")) {
            return 28;
        }

        if (userId.equals("B")) {
            return 32;
        }

        return 0;
    }
}
