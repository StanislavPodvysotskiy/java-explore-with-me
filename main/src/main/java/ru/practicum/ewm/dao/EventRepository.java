package ru.practicum.ewm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {

    @Query("select e from Event e where e.user.id = ?1")
    Page<Event> findAllWhereUserId(Integer userId, Pageable pageable);

    @Query("select e from Event e where e.id = ?1 and e.user.id = ?2")
    Event findByIdWhereUserId(Integer eventId, Integer userId);

    @Query("select e from Event e where e.id in ?1")
    List<Event> findAllIds(List<Integer> events);
}
