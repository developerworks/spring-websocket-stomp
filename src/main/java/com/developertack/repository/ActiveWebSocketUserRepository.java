package com.developertack.repository;

import com.developertack.repository.entity.ActiveWebSocketUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiveWebSocketUserRepository extends CrudRepository<ActiveWebSocketUser, String> {
    @Query("SELECT DISTINCT(u.username) FROM ActiveWebSocketUser u WHERE u.username != ?#{principal?.username}")
    List<String> findAllActiveUsers();
}
