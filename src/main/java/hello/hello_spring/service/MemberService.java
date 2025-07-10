package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// 서비스는 비즈니스에 가깝게 네이밍 설계를 함.
public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

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
