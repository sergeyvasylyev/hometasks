package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByIsDefault(Boolean isDefault);

    List<Account> findByActiveTrue();

}
