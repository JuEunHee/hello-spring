package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
//      각 테스트 실행마다 memberRepository를 생성해서 위 변수에 저장하고 new MemberService 시 생성된 repository를 넣어줌.
//      memberService 입장에서 내가 직접 new하지 않음. 외부에서 memoryMemberRepository를 외부에서 넣어줌.
//      이것을 DI, Depencency Injection(의존성 주입)이라고 함.
//      DI: 서비스 객체가 자신이 의존하는 리포지토리 객체의 구현체를 직접 생성하지 않고, 외부(설정 등)에서 전달받아 사용하는 설계 방식
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    // 과감하게 한글로 적기 가능, 프로덕션 코드에 포함되지 않음
    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then

        Member findMember = memberService.findOne(saveId).get();

        assertThat(member.getName()).isEqualTo(findMember.getName());
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