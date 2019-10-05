package ch.ranta.universal.tester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ch.ranta.universal.tester")
@EntityScan("ch.ranta.universal.tester")
public class App {
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(App.class, args);
	}
}
