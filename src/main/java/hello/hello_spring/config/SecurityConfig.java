package hello.hello_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

/**
 * 이것도 deprecated
 * @Overide
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
//   그 유저 정보는 security.properties에서 오고
//   유저 정보 보면 name, password, role 설정 가능

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

        http.formLogin(form -> {
        });
        http.httpBasic(httpBasic -> {
        });


        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 가장 널리 사용되는 비밀번호 인코더
    }

    // 2. 인메모리 사용자 상세 정보 서비스 Bean 등록
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) { // PasswordEncoder를 주입받습니다.
        UserDetails eunhee = User.builder()
                .username("eunhee")
                .password(passwordEncoder.encode("123")) // 비밀번호를 인코딩합니다.
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("!@")) // 비밀번호를 인코딩합니다.
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(eunhee, admin);
    }
}
