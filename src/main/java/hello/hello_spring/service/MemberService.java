package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 서비스는 비즈니스에 가깝게 네이밍 설계를 함.
// 서비스 어노테이션 등록해주면 오 서비스네 하고 스프링 컨테이너에 멤버 서비스 등록해줌
@Service
public class MemberService {
    // Command + Shift + T : 여기서 이 단축키로 바로 Test 만들기 가능

    // new MemoryMemberRepository();를 지우고 Ctrl+Enter 이용해서 constructor 만들어줌
    // 그래서 직접 내가 넣어주는게 아니라 외부에서 넣어주도록 바꾸는것.
    private final MemberRepository memberRepository;

//  memberService는 memberRepository가 필요함.
//  @Autowired가 되어있으면 이 멤버 서비스를 스프링이 생성할때
//  스프링이 뜰때 오 멤버 서비스네 하고 스프링 컨텐츠에 등록을 하면서 생성자 호출함.
//  이런식으로 등록해주는걸 "컴포넌트 스캔과 자동 의존관계 설정 방식"이라고 함.
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
//        ctrl + T : 리팩토링 단축키

          validateDuplicateMember(member);
//        아래를 간단하게 변환한 내용임.
//        // 같은 이름이 있는 중복 회원 X
//        // Command + Option + V : 리턴 해줌.
//        Optional<Member> result = memberRepository.findByName(member.getName());
//
//        // 아까 Optional을 한 번 감싸서 반환해줬고 그 감싼 덕분에 이렇게 '값이 있으면' 사용 가능
//        // - 아래 두 개 메소드도 많이 사용함.
//        //      * result.get()
//        //      * result.orElseGet()
//        result.ifPresent(m -> {
//           throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

       memberRepository.save(member);

       return member.getId();
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
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }
}
