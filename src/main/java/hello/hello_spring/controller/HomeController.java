package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 참고: 컨트롤러가 정적 파일보다 우선순위가 높다.

@Controller
public class HomeController {

    @GetMapping("/")
    public String Home() {
        return "home";
    }
}
