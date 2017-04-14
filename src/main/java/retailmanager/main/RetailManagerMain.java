package retailmanager.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"retailmanager.*"})
@SpringBootApplication
public class RetailManagerMain {
	
	public static void main(String[] args) {
		SpringApplication.run(RetailManagerMain.class, args);
		}
	
}
