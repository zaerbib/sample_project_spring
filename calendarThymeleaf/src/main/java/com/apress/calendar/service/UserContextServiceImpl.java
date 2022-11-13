package com.apress.calendar.service;

import com.apress.calendar.domain.CalendarUser;
import com.apress.calendar.repository.CalendarUserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserContextServiceImpl implements UserContextService{

    private final CalendarUserRepository calendarUserRepository;
    private int currentId = 0;

    public UserContextServiceImpl(CalendarUserRepository calendarUserRepository) {
        this.calendarUserRepository = calendarUserRepository;
    }

    @Override
    public CalendarUser getCurrentUser() {
        return calendarUserRepository.findById(currentId).get();
    }

    @Override
    public void setCurrentUser(CalendarUser user) {
        if(Objects.isNull(user)) {
            throw new IllegalArgumentException("user cannot be null");
        }
        Integer currentId = user.getId();

        if(currentId == null){
            throw new IllegalArgumentException("user.getId() cannot be null");
        }
        this.currentId = currentId;
    }
}
