package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Interface가 인터페이스를 받을때는 implements가 아니라 extends임.
// Interface는 다중 상속 가능
// JpaRepository<T(Member), ID(식별자)>

//  인터페이스만 있는데 이렇게 하면 스프링 데이터 JPA가 JPA Repository를 받고있으면 구현체를 자동으로 만들어줌.
//  그래서 구현체를 만들어서 스프링 빈을 자동으로 등록함.
//  우리는 가져다 쓰기만 하면됨.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

//  이렇게 하면 끝난것... ㅎㅎ 구현할 게 없음
    @Override
    Optional<Member> findByName(String name);
}
