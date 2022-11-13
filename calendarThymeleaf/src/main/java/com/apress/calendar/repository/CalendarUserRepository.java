package com.apress.calendar.repository;

import com.apress.calendar.domain.CalendarUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarUserRepository extends CrudRepository<CalendarUser, Integer> {
    @Query("select cal from CalendarUser cal where cal.email=?1")
    CalendarUser findUserByEmail(String email);

    @Query("select cal from CalendarUser cal where cal.email like %?1%")
    List<CalendarUser> findUsersByEmails(String email);
}
