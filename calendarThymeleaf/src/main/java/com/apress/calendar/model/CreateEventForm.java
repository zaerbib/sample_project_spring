package com.apress.calendar.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Data
public class CreateEventForm {
    @NotEmpty(message = "Attendee Email is required")
    @Email(message = "Attendee Email must be a valid email")
    private String attendeeEmail;
    @NotEmpty(message = "Summary is required")
    private String summary;
    @NotEmpty(message = "Description is required")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Event Date/Time is required")
    private Calendar when;
}
