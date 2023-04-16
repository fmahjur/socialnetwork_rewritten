package com.challenge.socialnetworkv2.data.repository;

import com.challenge.socialnetworkv2.data.entity.Friendship;
import com.challenge.socialnetworkv2.data.entity.Users;
import com.challenge.socialnetworkv2.data.enumeration.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByFirstUser(Users user);

    List<Friendship> findBySecondUser(Users user);

    @Query("select f from Friends f " +
            "where (f.firstUser= :first or f.secondUser= :first) and (f.firstUser= :second or f.secondUser=:second) ")
    Optional<Friendship> checkingFriendShip(Users first, Users second);

    @Modifying
    @Query("update Friendship f set f.friendshipStatus = :friendshipStatus " +
            "where (f.firstUser= :first or f.secondUser= :first) and (f.firstUser= :second or f.secondUser=:second)")
    void updateFriendShipStatus(Users first, Users second, FriendshipStatus friendshipStatus);
}
