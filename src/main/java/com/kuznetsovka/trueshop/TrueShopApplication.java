package com.kuznetsovka.trueshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TrueShopApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TrueShopApplication.class, args);
		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
		System.out.println(encoder.encode("pass"));
	}

}
