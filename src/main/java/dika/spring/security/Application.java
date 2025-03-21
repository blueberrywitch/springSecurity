package dika.spring.security;

import dika.spring.security.dto.LinksEntityDTO;
import dika.spring.security.dto.reqest.UserRequestDTO;
import dika.spring.security.enums.Roles;
import dika.spring.security.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		UserServiceImpl userService = context.getBean(UserServiceImpl.class);


	}


}
