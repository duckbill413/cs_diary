package com.example.springasync.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {
    private final EmailService emailService;

    /**
     * EmailService를 주입하여 사용
     * - 비동기로 동작할 수 있게 Sub Thread에게 위임
     */
    public void asyncCall_1() {
        System.out.println("[asyncCall_1] ::" + Thread.currentThread().getName()); // 현재 메소드를 실행하는 Thread name 출력
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }

    /**
     * Method 내부에 인스턴스 EmailService를 생성하고 Async 동작이 처리되는지 확인
     * - Spring Bean에 등록되지 않았기 때문에 동작하지 않는다.
     */
    public void asyncCall_2() {
        System.out.println("[asyncCall_2] ::" + Thread.currentThread().getName()); // 현재 메소드를 실행하는 Thread name 출력
        EmailService emailService = new EmailService();
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }

    /**
     * 내부 메서드를 사용해서 Async 동작 확인
     * 실수가 많은 케이스
     * 이미 AsyncService라는 Bean을 가져왔고 해당 Bean 안의 메서드를 바로 접근하게 되면 Spring의 도움을 받을 수 없음
     * 도움이란? Spring이 Async 메서드를 Proxy 객체로 Wrapping을 하고 그 Bean을 사용해야 하는데 AsyncService의 경우는 그냥 Bean을 가져오게 된다.
     */
    public void asyncCall_3() {
        System.out.println("[asyncCall_3] ::" + Thread.currentThread().getName()); // 현재 메소드를 실행하는 Thread name 출력
        sendMail();
    }

    @Async
    public void sendMail() {
        System.out.println("[sendMail] :: " + Thread.currentThread().getName());
    }
}
