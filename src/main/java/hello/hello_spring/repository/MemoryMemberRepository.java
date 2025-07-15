package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// MemberRepository에 Option + 엔터하면 implement 메서드 할 수 있음
// 요 MemberRepository 구현체에서 @Repository 어노테이션 추가해서 등록
@Repository
public class MemoryMemberRepository implements MemberRepository{

    // 1. 저장에는 DB 미정이므로 우선 Map을 씀. key는 아이디니까 Long, 값은 Member

    // 2. 실무에서는 동시성 문제가 있을 수 있어서 이렇게 공유되는 변수일때는
    //    Concurrent HashMap을 사용하는데 여기는 예제니까 단순히 HashMap 사용

    // 3. Test에서 new MemoryMemberRepository() 생성 시..
    //    : MemoryMemberRepository에서 현재 static으로 되어있어서
    //    static은 인스턴스랑 상관없이 클래스 레벨에 붙는거라 지금은 상관없는데 나중에
    //    그래도 new로 다른 객체 리포지토리가 생성되게 되면 혹시라도 뭔가 다른 인스턴스라서 다른 내용물이 생길 수 있다.
    private static Map<Long, Member> store = new HashMap<Long, Member>();
    // 이것도 동시성 문제 고려해서 atomic long 등등 해야하는데 우선 단순하게 LONG으로
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Optional일때 ofNullable로 감싸주면 클라이언트에서 처리 가능
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 루프로 돌림
            .filter(member -> member.getName().equals(name)) // 필터 돌리다가
            .findAny(); // 찾으면 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<Member>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
