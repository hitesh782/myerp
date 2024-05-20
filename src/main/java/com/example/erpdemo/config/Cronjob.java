package com.example.erpdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Cronjob {
	@Value("${cron.expression}")
	private String cronExpression;
	
	@Value("${server.port}")
	private String port;
	
	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	@Scheduled(cron = "${cron.expression}") 
	public void demoFunctionForCron() {
		log.info("Cron job running with expression: {} ",cronExpression);
		log.info("current port is {} and database is {}",port,databaseName);;
	}
}
