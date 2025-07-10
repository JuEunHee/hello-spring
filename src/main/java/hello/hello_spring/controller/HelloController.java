package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    // template 방식
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = true) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // API 방식
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello, " + name; // "hello, you"
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);

        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Control + Enter : getter, setter 자동 완성
    // getter, setter : "JavaBean 규약"

    // ResponseBody가 default로 json 형식으로 만들어서 반환하는것이 기본 정책임.

    // viewResolver 대신에 HttpMessageConverter가 동작함.
    // (JsonConverter, StringConverter)

    // 기본 문자처리 : StringHttpMessageHttpMessageConverter
    // 기본 객체처리 : MappingJackson2HttpMessageConverter

    // 참조 : 클라이언트 HTTP Accept 헤더와 컨트롤러 반환타입 정보 둘을 조합해서
    // HttpMessageConverter가 선택된다.
}
