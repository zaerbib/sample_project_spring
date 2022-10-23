package com.apress.calendar.service;

import com.apress.calendar.domain.CalendarUser;

public interface UserContext {
    /**
     * Gets the currently logged in {@link CalendarUser} or null if there is no authenticated user.
     *
     * @return
     */
    CalendarUser getCurrentUser();

    /**
     * Sets the currently logged in {@link CalendarUser}.
     * @param user the logged in {@link CalendarUser}. Cannot be null.
     * @throws IllegalArgumentException if the {@link CalendarUser} is null.
     */
    void setCurrentUser(CalendarUser user);
}
