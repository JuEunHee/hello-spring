package hello.hello_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;


/**
 * WebSecurityConfigurerAdapter is deprecated
 *
 * before:
 *
 * @Configuration
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> { // 여기에 중괄호({})를 추가하여 람다 블록을 명확히 하고, return 문을 사용하지 않습니다.
                authorize
                        .requestMatchers("/", "/info").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated(); // 마지막에 세미콜론(;)을 붙여 문장으로 만듭니다.
        });

        http.formLogin(form -> {});
        http.httpBasic(httpBasic -> {});


        return http.build();
    }
}
