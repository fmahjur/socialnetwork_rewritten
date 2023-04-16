package com.challenge.socialnetworkv2.data.entity;

import com.challenge.socialnetworkv2.data.enumeration.FriendshipStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    Users firstUser;

    @OneToOne(cascade = CascadeType.ALL)
    Users secondUser;

    @CreationTimestamp
    LocalDateTime friendshipDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    FriendshipStatus friendshipStatus;

    public Friendship(Users firstUser, Users secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.friendshipStatus = FriendshipStatus.REQUESTED;
    }
}
