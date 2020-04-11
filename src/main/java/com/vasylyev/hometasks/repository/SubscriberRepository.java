package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    public List<Subscriber> findByActiveTrue();

    public Subscriber findByChatId(String chatId);

}
