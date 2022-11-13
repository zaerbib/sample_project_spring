package com.apress.calendar.service;

import com.apress.calendar.domain.CalendarUser;
import com.apress.calendar.domain.Event;

import java.util.List;

public interface CalendarService {
    CalendarUser getUser(int id);
    CalendarUser findUserByEmail(String email);
    List<CalendarUser> findUsersByEmail(String partialEmail);
    int createUser(CalendarUser user);
    Event getEvent(int eventId);
    int createEvent(Event event);
    List<Event> findForUSer(int userid);
    List<Event> getEvents();
}
