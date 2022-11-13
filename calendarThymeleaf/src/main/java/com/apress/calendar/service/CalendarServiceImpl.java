package com.apress.calendar.service;

import com.apress.calendar.domain.CalendarUser;
import com.apress.calendar.domain.Event;
import com.apress.calendar.repository.CalendarUserRepository;
import com.apress.calendar.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService{

    private final EventRepository eventRepository;
    private final CalendarUserRepository calendarUserRepository;

    public CalendarServiceImpl(EventRepository eventRepository, CalendarUserRepository calendarUserRepository) {
        if(eventRepository == null) {
            throw new IllegalArgumentException("eventReposiroty cannot be null");
        }
        if(calendarUserRepository == null) {
            throw new IllegalArgumentException("celendarUserRepo cannot be null");
        }
        this.eventRepository = eventRepository;
        this.calendarUserRepository = calendarUserRepository;
    }

    @Override
    public CalendarUser getUser(int id) {
        return calendarUserRepository.findById(id).get();
    }

    @Override
    public CalendarUser findUserByEmail(String email) {
        return calendarUserRepository.findUserByEmail(email);
    }

    @Override
    public List<CalendarUser> findUsersByEmail(String partialEmail) {
        return calendarUserRepository.findUsersByEmails(partialEmail);
    }

    @Override
    public int createUser(CalendarUser user) {
        return calendarUserRepository.save(user).getId();
    }

    @Override
    public Event getEvent(int eventId) {
        return eventRepository.findById(eventId).get();
    }

    @Override
    public int createEvent(Event event) {
        return eventRepository.save(event).getId();
    }

    @Override
    public List<Event> findForUSer(int userid) {
        return eventRepository.findForUser(userid);
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
}
