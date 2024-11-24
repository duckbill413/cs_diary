package wh.duckbill.starter;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LoginController {
    private final HashMap<String, String> sessionMap = new HashMap<>();

    @GetMapping("/login")
    public String login(@RequestParam String name, HttpSession session) {
        sessionMap.put(session.getId(), name);
        return "saved.";
    }

    @GetMapping("/myName")
    public String myName(HttpSession session) {
        return sessionMap.get(session.getId());
    }
}
