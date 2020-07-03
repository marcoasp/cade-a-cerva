package br.com.marco.cadeacerva.users;

import br.com.marco.cadeacerva.users.domain.User;
import br.com.marco.cadeacerva.users.domain.UserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsersApplication {

	@Autowired
	private UserProducer userProducer;

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return (args) -> userProducer.produceUserMessage(new User("random-string", "existing-user@email.com", new double[]{10.0, 20.5}, 3.5));
	}

}
