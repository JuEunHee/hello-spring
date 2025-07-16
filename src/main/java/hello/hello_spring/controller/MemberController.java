package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 처음에 뜰 때 Spring Container 생김
// @Controller 어노테이션이 있으면 MemberController 객체를 생성을 해서 스프링에 넣어두면 스프링이 관리함.
// "스프링 컨테이너에서 스프링 빈이 관리된다"라고 표현
@Controller
public class MemberController {
//  new 해서 하면 멤버는 여러군데서 쓰니까
//  멤버 컨트롤러 뿐만아니라 주문 컨트롤러같은 다양한 곳에서 멤버 서비스를 가져다 쓸 수 있을것
//  멤버 서비스는 여러개 생성할 필요가 없고 공용으로 쓰면 되는것.
//  이럴때면 Spring Container에 등록하면 딱 하나만 등록됨.

    private final MemberService memberService;

//  멤버 컨트롤러가 스프링 컨테이너가 뜰때 생성자로 호출.
//  Autowired라고 되어있으면 멤버 컨트롤러를 스프링 컨테이너가 뜰때 생성함. 그때 이 생성자로 호출.
//  생성자에 Autowired라고 되어있으면 스프링이 스프링 컨테이너에 있는 멤버 서비스를 가져다가 연결을 해줌.

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();

        // 컨트롤러는 Model 객체에 `addAttribute` 메소드를 사용하여 뷰 템플릿에 전달할 데이터를 담습니다.
        // 뷰 리졸버가 템플릿을 찾고 Thymeleaf가 이 데이터를 렌더링합니다.
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
