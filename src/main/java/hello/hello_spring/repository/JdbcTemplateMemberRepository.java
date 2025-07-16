package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

//  생성자가 딱 하나만 있으면 스프링 빈으로 등록되서 Autowired를 생략할 수 있음
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
//      아래 4줄로 insert문 만들어짐. (id, name) 넣는 내용.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
//      Template인 이유 : 디자인 패턴 중 템플릿 메서드 패턴 아이디어 차용해서 해결
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {

        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

//  로우 맵퍼라는걸로 맵핑해야함.
    private RowMapper<Member> memberRowMapper() {
//      mapRow(ResultSet rs, int rowNum) throws SQLException {}..하다가
//      Option+Enter로 자바8 람다로 바꾸기 하면 아래와 같은 결과 나옴
        return (rs, rowNum) -> {
            Member member = new Member();

            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));

            return member;
        };
    }
}
