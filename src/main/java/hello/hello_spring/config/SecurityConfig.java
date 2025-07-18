package hello.hello_spring.config;

import hello.hello_spring.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


/*
  WebSecurityConfigurerAdapter is deprecated

  before:

  @Configuration
 * @EnableWebSecurity
 * public class SecurityConfig extends WebSecurityConfigurerAdapter {
 *
 *    @Override
 *    protected void configure(HttpSecurity http) throws Exception {
 *      http.cors().and().csrf().disable()
 *          .and().authorizeRequests()
 *          .antMatchers("/home").permitAll()
 *          .antMatchers("/mypage").authenticated()
 *          .anyRequest().authenticated()
 *      ;
 *    }
 * }
 */

/**
 * 이것도 deprecated
 * &#064;Override
 * protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 * noop: 기본 패스워드 인코더
 * (앞에 반드시 prefix로 뒤에 있는 패스워드가 어떻게 인코딩 되어있는지 알려주면 prefix 보고 복호화
 *     auth.inMemoryAuthentication()
 *             .withUser("eunhee").password([noob비밀번호]).roles("USER").and()
 *             .withUser("admin").password([noob비밀번호]).roles("ADMIN");
 * }
 */

// UserDetailsServiceAutoConfiguration
// : 이걸로 유저 정보를 자동 생성. inMemoryUserDetailsManager를 써서 유저 등록
//   그 유저 정보는 security.properties 에서 오고
//   유저 정보 보면 name, password, role 설정 가능

//
//    만약 account 서비스를 명시적으로 선언하고싶다면
//    유저 디테일 서비스 구현체를 써서 유저 정보는 데이터베이스를 가져와서 쓰렴 하고 아래처럼 등록해야함.
//    하지만 이렇게 하지 않아도 유저 디테일 서비스타입의 빈이 등록만 되어있으면 가져다 쓰게됨. 생략.
//    @Autowired
//    AccountService accountService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(accountService);
//    }

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> { // 여기에 중괄호({})를 추가하여 람다 블록을 명확히 하고, return 문을 사용하지 않습니다.
            authorize
                    .requestMatchers("/", "/info", "/account/**").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated(); // 마지막에 세미콜론(;)을 붙여 문장으로 만듭니다.
        });

        http.formLogin(form -> {
        });
        http.httpBasic(httpBasic -> {
        });

        return http.build();
    }



}
