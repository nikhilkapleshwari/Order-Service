package io.api.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "io.api")
@EnableJpaRepositories(basePackages = {"io.api.repository"})
@EntityScan(basePackages = {"io.api.entity"} )
@EnableEurekaClient
public class OrderServiceApplication {

  public static void main(String[] args) {
	SpringApplication.run(OrderServiceApplication.class, args);
  }
  
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
	return new RestTemplate();
  }

}
