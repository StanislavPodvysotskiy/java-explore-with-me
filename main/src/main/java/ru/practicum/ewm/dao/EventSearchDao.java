package ru.practicum.ewm.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.ewm.emun.State;
import ru.practicum.ewm.exception.EndBeforeStartException;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventSearchDao {

    private final EntityManager em;

    public EventSearchDao(EntityManager em) {
        this.em = em;
    }

    public List<Event> findByParametersAdmin(List<User> users, List<State> states, List<Category> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> root = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (users != null) {
            Predicate user = root.get("user").in(users);
            predicates.add(user);
        }
        if (states != null) {
            Predicate state = root.get("state").in(states);
            predicates.add(state);
        }
        if (categories != null) {
            Predicate category = root.get("category").in(categories);
            predicates.add(category);
        }
        if (rangeStart != null) {
            Predicate start = cb.greaterThan(root.get("eventDate"), rangeStart);
            predicates.add(start);
        }
        if (rangeEnd != null) {
            Predicate end = cb.lessThan(root.get("eventDate"), rangeEnd);
            predicates.add(end);
        }
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new EndBeforeStartException("End can't be before Start");
        }
        Predicate andPredicate = cb.and(predicates.toArray(new Predicate[]{}));
        cq.where(andPredicate);
        TypedQuery<Event> query = em.createQuery(cq);
        return query.setMaxResults(size).setFirstResult(from).getResultList();
    }

    public List<Event> findByParametersPublic(String text, List<Category> categories, Boolean paid,
                                              LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                              Integer from, Integer size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> root = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> textPredicate = new ArrayList<>();
        if (text != null) {
            Predicate textAnnotation = cb.like(root.get("annotation"), "%" + text + "%");
            Predicate textDescription = cb.like(root.get("description"), "%" + text + "%");
            textPredicate.add(textAnnotation);
            textPredicate.add(textDescription);
        }
        if (categories != null) {
            Predicate category = root.get("category").in(categories);
            predicates.add(category);
        }
        if (paid != null) {
            Predicate isPaid = cb.equal(root.get("paid"), paid);
            predicates.add(isPaid);
        }
        if (rangeStart != null) {
            Predicate start = cb.greaterThan(root.get("eventDate"), rangeStart);
            predicates.add(start);
        }
        if (rangeEnd != null) {
            Predicate end = cb.lessThan(root.get("eventDate"), rangeEnd);
            predicates.add(end);
        }
        Predicate state = cb.equal(root.get("state"), State.PUBLISHED);
        predicates.add(state);
        Predicate orPredicate = cb.or(textPredicate.toArray(new Predicate[]{}));
        Predicate andPredicate = cb.and(predicates.toArray(new Predicate[]{}));
        cq.where(orPredicate, andPredicate);
        TypedQuery<Event> query = em.createQuery(cq);
        return query.setMaxResults(size).setFirstResult(from).getResultList();
    }
}
