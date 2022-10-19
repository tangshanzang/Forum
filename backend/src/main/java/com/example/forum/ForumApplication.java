package com.example.forum;

import com.example.forum.entity.AppUser;
import com.example.forum.entity.Role;
import com.example.forum.service.UserServices.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.createRole(new Role(null, "ROLE_USER"));
			userService.createRole(new Role(null, "ROLE_ADMIN"));

			userService.createUser(new AppUser(null, "YangLi", "1234", "Yang Li",
					LocalDateTime.now(), new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),
					new ArrayList<>(),"active"));
			userService.createUser(new AppUser(null, "TianLi", "1234", "Tian Li",
					LocalDateTime.now(), new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),
					new ArrayList<>(),"active"));

			userService.addRoleToUser("YangLi", "ROLE_ADMIN");
			userService.addRoleToUser("TianLi", "ROLE_TRAINEE");

		};
	}

}
