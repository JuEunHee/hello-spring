package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// `@SpringBootTest` : 스프링 컨테이너와 테스트를 함께 실행한다.
// `@Transactional` : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트
// 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // 과감하게 한글로 적기 가능, 프로덕션 코드에 포함되지 않음
    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    // 중복 회원이 터트려지는 것도 봐야함.
    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
//        CASE 1: try-catch하는 방법이 있고..
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

//        CASE 2: assertThrows라는 문법을 사용할 수 있음. 그리고 람다로 넘어가는것.
//        만약 IllegalStateException이 아니라 NullPointerException같은 다른 에러로 해보면 에러 터질것.
        memberService.join(member1);
//        반환 단축키 : Command+Option+V
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}