package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    List<Subscriber> findByActiveTrue();

    Optional<Subscriber> findByChatId(String chatId);

}
