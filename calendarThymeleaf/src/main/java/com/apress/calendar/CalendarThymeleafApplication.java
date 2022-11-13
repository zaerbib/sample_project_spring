package com.apress.calendar;

import com.apress.calendar.domain.CalendarUser;
import com.apress.calendar.domain.Event;
import com.apress.calendar.repository.CalendarUserRepository;
import com.apress.calendar.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.GregorianCalendar;

@SpringBootApplication
public class CalendarThymeleafApplication {

	private final CalendarUserRepository userRepository;
	private final EventRepository eventRepository;

	public CalendarThymeleafApplication(CalendarUserRepository userRepository, EventRepository eventRepository) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CalendarThymeleafApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(){
		return (args -> {
			CalendarUser user1, user2, user3;
			Event event1, event2, event3;
			user1 = new CalendarUser(0, "user1@example.com", "user1", "User", "1");
			user2 = new CalendarUser(1, "admin1@example.com", "admin1", "Admin", "1");
			user3 = new CalendarUser(2 , "user2@example.com", "user2", "User", "2");

			event1 = new Event(100,
					new GregorianCalendar(2017,7,3,20,30,0),
					"Birthday Party",
					"This is going to be a great birthday", user1, user2);
			event2 = new Event(101,
					new GregorianCalendar(2017, 12, 23, 13, 0, 0),
					"Conference Call",
					"Call with the client", user3, user1);

			event3 = new Event(102,
					new GregorianCalendar(2018, 1, 23, 11, 30, 0),
					"Lunch",
					"Eating lunch together", user2, user3);

			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			eventRepository.save(event1);
			eventRepository.save(event2);
			eventRepository.save(event3);

		});
	}
}
