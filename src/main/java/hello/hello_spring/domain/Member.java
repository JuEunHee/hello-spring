package hello.hello_spring.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

//  DB에 값을 넣으면 DB가 id를 자동으로 생성해주는 것을 identity 전략이라고 부름.
//  Oracle같은 db같은 경우에 sequence 쓰기도 하고 테이블에서 체번할 수도 있고 여러 방법이 있음.
//  이렇게 db가 알아서 생성해주는건 Identity
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
