package hello.hello_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		//	run해보면 Tomcat started on port 8080 (http) with context path '/'라는 메시지와 함께 8080으로 뜸.
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
