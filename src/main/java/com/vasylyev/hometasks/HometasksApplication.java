package com.vasylyev.hometasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HometasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(HometasksApplication.class, args);
	}

	/*
	*
	* UI:
	* - UI to angular
	* - settings to UI
	* 		- (telegram)
	* 		- google
	* DB:
	* - settings to DB
	* 		- enum with setting name
	* 		- all data to db not to app properties
	* Auth:
	* - move auth to UI
	* Common:
	* - use app without proper setting
	* - several accounts
	* 		- link telegram bot with subscribers
	* 		- add account id
	* 		- ad account to courses
	* - google sheet - optional
	* - telegram bot optional
	* - scheduler - run only if proper settings are filled in
	*
	* */

}
