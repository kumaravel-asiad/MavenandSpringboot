package com.nxtlvl.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages={"com.nxtlvl.springboot"}) // same as @Configuration @EnableAutoConfiguration @ComponentScan 
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
