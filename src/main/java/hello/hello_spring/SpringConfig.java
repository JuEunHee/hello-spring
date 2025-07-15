package hello.hello_spring;

import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 첫번째 방법은 컴포넌트 스캔과 Autowired 방법이었다면,
// 아래 두번째 방법은 설정 파일 만들어서 명시적으로 알려주고 한번에 관리 가능.
//
// 두번째 방법 : 자바 코드로 명시적으로 직접 스프링 빈 등록하기 (아래와 같음)

// 과거에는 XML로 설정했는데 요즘은 잘 사용하지 않음.
@Configuration
public class SpringConfig {

//  @Bean => 내가 스프링 빈을 직접 등록할거야
    @Bean
    public MemberService memberService(){
//      Ctrl+P로 뭐 넣어줘야하는지 검색 가능
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
