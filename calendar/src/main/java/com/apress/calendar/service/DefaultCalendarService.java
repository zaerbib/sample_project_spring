package com.apress.calendar.service;

import com.apress.calendar.dataaccess.CalendarUserDao;
import com.apress.calendar.dataaccess.EventDao;
import com.apress.calendar.domain.CalendarUser;
import com.apress.calendar.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCalendarService implements CalendarService{
    private final EventDao eventDao;
    private final CalendarUserDao userDao;

    @Autowired
    public DefaultCalendarService(EventDao eventDao, CalendarUserDao userDao) {
        if (eventDao == null) {
            throw new IllegalArgumentException("eventDao cannot be null");
        }
        if (userDao == null) {
            throw new IllegalArgumentException("userDao cannot be null");
        }

        this.eventDao = eventDao;
        this.userDao = userDao;
    }


    @Override
    public CalendarUser getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public CalendarUser findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public List<CalendarUser> findUsersByEmail(String partialEmail) {
        return userDao.findUsersByEmail(partialEmail);
    }

    @Override
    public int createUser(CalendarUser user) {
        return userDao.createUser(user);
    }

    @Override
    public Event getEvent(int eventId) {
        return eventDao.getEvent(eventId);
    }

    @Override
    public int createEvent(Event event) {
        return eventDao.createEvent(event);
    }

    @Override
    public List<Event> findForUser(int userId) {
        return eventDao.findForUser(userId);
    }

    @Override
    public List<Event> getEvents() {
        return eventDao.getEvents();
    }
}
