package ru.practicum.ewm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "COMPILATIONS")
@Getter
@Setter
@NoArgsConstructor
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany
    @JoinTable(name = "COMPILATIONS_EVENTS",
              joinColumns = @JoinColumn(name = "compilation_id"),
              inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;
    private Boolean pinned;
    private String title;
}
