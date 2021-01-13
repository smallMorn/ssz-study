package com.ssz.service.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication(scanBasePackages = "com.ssz")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.ssz.service.product.mapper")
public class ServiceProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProductApplication.class, args);
	}

}
