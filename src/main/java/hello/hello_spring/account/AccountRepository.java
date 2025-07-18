package hello.hello_spring.account;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA가 제공해주는 기능
// -> 인터페이스만 만들어도 이 인터페이스에 구현체가 자동으로 만들어지고 빈으로 등록이 된다.
// 그래서 이미 있는것처럼 사용할 수 있음.
// (Repository만 만들었지만 AccountRepository라는 구현체가 있고 마치 빈으로 등록이 이미 등록된것처럼 사용 가능
public interface AccountRepository extends JpaRepository<Account, Integer> {


    Account findByUsername(String username);
}
