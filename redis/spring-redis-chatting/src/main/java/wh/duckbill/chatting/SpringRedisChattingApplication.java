package wh.duckbill.chatting;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wh.duckbill.chatting.service.ChatService;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringRedisChattingApplication implements CommandLineRunner {
    private final ChatService chatService;

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisChattingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started");

        chatService.enterChatRoom("chat1");
    }
}
