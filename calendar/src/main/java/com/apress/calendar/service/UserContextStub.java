package com.apress.calendar.service;

import com.apress.calendar.dataaccess.CalendarUserDao;
import com.apress.calendar.domain.CalendarUser;
import org.springframework.stereotype.Service;

@Service
public class UserContextStub implements UserContext {
    private final CalendarUserDao userDao;
    private int currentId = 0;

    public UserContextStub(CalendarUserDao userDao) {
        if (userDao == null) {
            throw new IllegalArgumentException("userService cannot be null");
        }
        this.userDao = userDao;
    }

    @Override
    public CalendarUser getCurrentUser() {
        return userDao.getUser(currentId);
    }

    @Override
    public void setCurrentUser(CalendarUser user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        Integer currentId = user.getId();
        if(currentId == null) {
            throw new IllegalArgumentException("user.getId() cannot be null");
        }
        this.currentId = currentId;
    }
}
