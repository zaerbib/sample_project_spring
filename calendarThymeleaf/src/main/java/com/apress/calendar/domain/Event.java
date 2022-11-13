package com.apress.calendar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "When is required")
    @Column(name = "dateWhen")
    private Calendar dateWhen;

    @NotEmpty(message = "Summary is required")
    private String summary;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotNull(message = "Owner is required")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private CalendarUser owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attendee", referencedColumnName = "id")
    private CalendarUser attendee;

}
