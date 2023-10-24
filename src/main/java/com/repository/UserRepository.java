package com.repository;

import com.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("select u from User u")
    Page<User> findAllUser(Pageable pageable);
    Page<User> findByUsername(String username, Pageable pageable);
    Page<User> findByUsernameAndEmail(String username, String email, Pageable pageable);

    boolean existsByEmail(String email);
}
