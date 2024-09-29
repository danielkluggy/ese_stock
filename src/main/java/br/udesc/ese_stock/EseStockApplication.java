package br.udesc.ese_stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EseStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(EseStockApplication.class, args);
	}

}
