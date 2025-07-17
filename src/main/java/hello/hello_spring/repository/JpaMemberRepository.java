package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class JpaMemberRepository implements MemberRepository {

//  JPA는 엔티티 매니저라는 걸로 모든게 동작함.
//  빌드전 Gradle해서 data-jpa 라이브러리를 받았음.
//  그렇게 하면 스프링 부타가 자동으로 엔티티 매니저를 생성해주고
//  현재 데이터베이스랑 연결해서 만들어줌.
//  그래서 우리는 이 만들어진거를 인젝션받으면 되는 것.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
//      이렇게 하면 JPA가 인서트 쿼리 만들어서 db에 집어넣고 id까지 멤버에 setId 다 해줌.
//      모든걸 다 처리해준다.
        em.persist(member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
//      조회할 타입이랑 식별자 pk값만 넣어주면 끝!
        Member member = em.find(Member.class, id);

        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
//      JPQL이라는 객체지향 언어를 써야함.
//      객체를 대상으로 쿼리를 날리는 것.

//      select m이라고 하는 부분 주의

//      Command+Option+N: 인라인이라고 하면 const result = ...; return ...;가 한 줄로 합쳐짐.
//      Ctrl+T로도 검색 가능
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
