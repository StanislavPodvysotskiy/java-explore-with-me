package ru.practicum.ewm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.emun.State;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "EVENTS")
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "created")
    private LocalDateTime createdOn = LocalDateTime.now();
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    private Double lat;
    private Double lon;
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    private String title;
    @Enumerated(EnumType.STRING)
    private State state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "events")
    private List<Compilation> compilations;
    private Double rate;
    @ManyToMany
    @JoinTable(name = "LIKES",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes;
    @ManyToMany
    @JoinTable(name = "DISLIKES",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> dislikes;

    public void addLike(User user) {
        likes.add(user);
        setRate();
    }
    public void addDislike(User user) {
        dislikes.add(user);
        setRate();
    }

    public void removeLike(User user) {
        likes.remove(user);
        setRate();
    }

    public void removeDislike(User user) {
        dislikes.remove(user);
        setRate();
    }

    private void setRate() {
        if (likes.size() > 0 || dislikes.size() > 0) {
            rate = (double) likes.size() / (likes.size() + dislikes.size());
        } else {
            rate = 0.00;
        }
    }
}
