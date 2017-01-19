package com.deloittelabs.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class MicroserviceDockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceDockerApplication.class, args);
	}

	private static final Logger LOG = LoggerFactory.getLogger(MicroserviceDockerApplication.class.getName());

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/")
	public String home() {
		LOG.info("you called home");
		return "Welcome to Deloitte Labs Zipkin tracing example";
	}

	@RequestMapping("/callMSHome")
	public String callHome() {
		LOG.info("calling home");
		return restTemplate.getForObject("http://localhost:9083", String.class);
	}

	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}

}
