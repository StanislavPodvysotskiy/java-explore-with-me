package ru.practicum.ewm.hit.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HITS")
@Getter
@Setter
@NoArgsConstructor
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String app;
    private String uri;
    private String ip;
    @Column(name = "time_stamp")
    private LocalDateTime timestamp;
}