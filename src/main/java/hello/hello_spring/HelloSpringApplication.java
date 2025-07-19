package hello.hello_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HelloSpringApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	public static void main(String[] args) {
		//	run해보면 Tomcat started on port 8080 (http) with context path '/'라는 메시지와 함께 8080으로 뜸.
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
