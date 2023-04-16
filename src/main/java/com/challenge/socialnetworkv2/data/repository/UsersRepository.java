package com.challenge.socialnetworkv2.data.repository;

import com.challenge.socialnetworkv2.data.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}
