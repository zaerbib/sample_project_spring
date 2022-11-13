package com.apress.calendar.repository;

import com.apress.calendar.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("select eve from Event eve where eve.owner.id=?1 or eve.attendee.id=?1")
    List<Event> findForUser(int id);
}
