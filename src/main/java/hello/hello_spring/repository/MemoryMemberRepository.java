package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

// MemberRepository에 Option + 엔터하면 implement 메서드 할 수 있음
public class MemoryMemberRepository implements  MemberRepository{

    // 저장에는 DB 미정이므로 우선 Map을 씀. key는 아이디니까 Long, 값은 Member

    // 실무에서는 동시성 문제가 있을 수 있어서 이렇게 공유되는 변수일때는
    // Concurrent HashMap을 사용하는데 여기는 예제니까 단순히 HashMap 사용
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
}
