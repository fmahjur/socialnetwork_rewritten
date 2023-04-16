package com.challenge.socialnetworkv2.data.entity;

import com.challenge.socialnetworkv2.data.enumeration.Role;
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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String firstname;

    @Column(nullable = false)
    String lastname;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @CreationTimestamp
    LocalDateTime registeryDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    Role role;
}
