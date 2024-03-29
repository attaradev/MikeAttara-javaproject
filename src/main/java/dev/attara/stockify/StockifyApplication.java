package dev.attara.stockify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class to start the Stockify application.
 */
@SpringBootApplication
public class StockifyApplication {

	/**
	 * The main method to start the Stockify application.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(StockifyApplication.class, args);
	}

}
