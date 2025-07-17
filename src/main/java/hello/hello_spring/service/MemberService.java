package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 서비스는 비즈니스에 가깝게 네이밍 설계를 함.
// 서비스 어노테이션 등록해주면 오 서비스네 하고 스프링 컨테이너에 멤버 서비스 등록해줌

//  JPA를 쓰려면 항상 주의해야할 게 Transactional을 설정해줘야함.
//  모든 데이터 변경이 트랜잭션 안에서 실행되어야한다는것.
@Transactional
public class MemberService {
    // Command + Shift + T : 여기서 이 단축키로 바로 Test 만들기 가능

    // new MemoryMemberRepository();를 지우고 Ctrl+Enter 이용해서 constructor 만들어줌
    // 그래서 직접 내가 넣어주는게 아니라 외부에서 넣어주도록 바꾸는것.
    private final MemberRepository memberRepository;

//  memberService는 memberRepository가 필요함.
//  @Autowired가 되어있으면 이 멤버 서비스를 스프링이 생성할때
//  스프링이 뜰때 오 멤버 서비스네 하고 스프링 컨텐츠에 등록을 하면서 생성자 호출함.
//  이런식으로 등록해주는걸 "컴포넌트 스캔과 자동 의존관계 설정 방식"이라고 함.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {

        long start = System.currentTimeMillis();

        try {
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {

//      회원가입, 회원 조회에 시간을 측정하는 기능은 "핵심 관심 사항(Core concern)"이 아니다.
//      시간을 측정하는 로직은 "공통 관심 사항(Cross-cutting concern"이다.
//      시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다.
//      시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다.
//      시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.

        long start = System.currentTimeMillis();

        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = " + timeMs + "ms");
        }
    }

    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }
}
