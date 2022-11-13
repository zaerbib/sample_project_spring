package com.apress.calendar.service;

import com.apress.calendar.domain.CalendarUser;

public interface UserContextService {
    CalendarUser getCurrentUser();
    void setCurrentUser(CalendarUser user);
}
