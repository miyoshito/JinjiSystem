package aimyamaguchi.co.jp.aimspringsql;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AimSpringJinjiSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AimSpringJinjiSystemApplication.class, args);
	}

	@Bean
	protected Module module() {
		return new Hibernate5Module();
	}

}
