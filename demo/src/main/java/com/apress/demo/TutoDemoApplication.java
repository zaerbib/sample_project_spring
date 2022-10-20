package com.apress.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class TutoDemoApplication implements CommandLineRunner, ApplicationRunner {
	private static final Logger log = LoggerFactory.getLogger(TutoDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TutoDemoApplication.class, args);
	}

	@Bean
	String info(){
		return "Just a simple String bean";
	}

	String info;

	@Value("${myapp.server-ip}")
	String serverIp;

	@Autowired
	MyAppProperties props;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("## > ApplicationRunner Implementation...");
		log.info("Accessing the info bean: "+info());
		args.getNonOptionArgs().forEach(file -> log.info(file));
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("## > CommandLineRunner Implementation...");
		log.info("Accessing the Info bean: "+info());
		log.info(" > The Server IP is : "+serverIp);
		log.info(" > App Name: "+props.getName());
		log.info(" > App Info: "+props.getDescription());
		for(String arg: args)
			log.info(arg);
	}

	@Component
	@ConfigurationProperties(prefix = "myapp")
	public static class MyAppProperties {
		private String name;
		private String description;
		private String serverIp;

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getServerIp() {
			return serverIp;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setServerIp(String serverIp) {
			this.serverIp = serverIp;
		}
	}
}
