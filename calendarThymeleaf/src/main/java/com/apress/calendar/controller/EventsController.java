package com.apress.calendar.controller;

import com.apress.calendar.domain.CalendarUser;
import com.apress.calendar.domain.Event;
import com.apress.calendar.model.CreateEventForm;
import com.apress.calendar.service.CalendarService;
import com.apress.calendar.service.UserContextService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Calendar;

@Controller
@RequestMapping("/events")
public class EventsController {

    private final CalendarService calendarService;
    private final UserContextService userContextService;

    public EventsController(CalendarService calendarService, UserContextService userContextService) {
        this.calendarService = calendarService;
        this.userContextService = userContextService;
    }

    @GetMapping("/")
    public ModelAndView events() {
        return new ModelAndView("events/list", "events", calendarService.getEvents());
    }

    @GetMapping("/my")
    public ModelAndView myEvents() {
        CalendarUser currentUser = userContextService.getCurrentUser();
        Integer currentUserId = currentUser.getId();
        ModelAndView result = new ModelAndView("events/my", "events", calendarService.findForUSer(currentUserId));
        return result;
    }

    @GetMapping("/{eventId}")
    public ModelAndView show(@PathVariable int eventId) {
        Event event = calendarService.getEvent(eventId);
        return new ModelAndView("events/show", "event", event);
    }

    @GetMapping("/form")
    public String createEventForm(@ModelAttribute CreateEventForm createEventForm) {
        return "events/create";
    }

    @PostMapping(value = "/new", params = "auto")
    public String createEventFormAutoPopulate(@ModelAttribute CreateEventForm createEventForm) {
        createEventForm.setSummary("A new event ...");
        createEventForm.setDescription("This was autopopulated to save time creating a valid event.");
        createEventForm.setWhen(Calendar.getInstance());

        CalendarUser currentUser = userContextService.getCurrentUser();
        int attendeeId = currentUser.getId() == 0 ? 1 : 0;
        CalendarUser attendee = calendarService.getUser(attendeeId);
        createEventForm.setAttendeeEmail(attendee.getEmail());

        return "events/create";
    }

    @PostMapping(value = "/new")
    public String createEvent(@Valid CreateEventForm createEventForm,
                              BindingResult result,
                              RedirectAttributes redirectAttributes){
        if(result.hasErrors()) {
            return "events/create";
        }
        CalendarUser attendee = calendarService.findUserByEmail(createEventForm.getAttendeeEmail());
        if(attendee == null) {
            result.rejectValue("attendeeEmail", "attendeeEmail.missing",
                    "Could not find a user for the provided Attendee Email");
        }
        if(result.hasErrors()){
            return "events/create";
        }

        Event event = new Event();
        event.setAttendee(attendee);
        event.setDescription(createEventForm.getDescription());
        event.setOwner(userContextService.getCurrentUser());
        event.setSummary(createEventForm.getSummary());
        event.setDateWhen(createEventForm.getWhen());

        calendarService.createEvent(event);

        redirectAttributes.addFlashAttribute("message", "Succesfully added the new event");

        return "redirect:/events/my";
    }
}
