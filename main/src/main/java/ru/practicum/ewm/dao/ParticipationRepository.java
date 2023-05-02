package ru.practicum.ewm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Participation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    @Query("select p from Participation p where p.requester.id = ?1 and p.event.id = ?2")
    Optional<Participation> findParticipation(Integer userId, Integer eventId);

    @Query("select p from Participation p where p.requester.id = ?1")
    List<Participation> findAllByUserId(Integer userId);

    List<Participation> findByIdIn(List<Integer> requestIds);

    @Query("select p from Participation p where p.event.id = ?2 and p.event.user.id = ?1")
    List<Participation> findAllByOwnerIdAndEventId(Integer userId, Integer eventId);

    @Query("select count(p) from Participation p where p.event.id = ?1 and p.status = 'CONFIRMED'")
    Integer findCountRequestsByEventId(Integer eventId);

    @Query("select p.event.id, count(p) from Participation p where p.event in ?1 and p.status = 'CONFIRMED' " +
            "group by p.event.id")
    Map<Integer, Integer> getRequestCountMap(List<Event> events);
}
