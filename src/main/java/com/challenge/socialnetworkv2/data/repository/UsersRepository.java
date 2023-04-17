package com.challenge.socialnetworkv2.data.repository;

import com.challenge.socialnetworkv2.data.entity.Users;
import com.challenge.socialnetworkv2.data.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);

    Optional<Users>  findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.enabled = TRUE WHERE u.email= :email and u.role= :role")
    void enableUsers(String email, Role role);
}
