package ru.practicum.ewm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.id in ?1")
    Page<User> findAllIn(List<Integer> ids, Pageable pageable);

    @Query("select u from User u where u.id in ?1")
    List<User> findAllIn(List<Integer> ids);
}
