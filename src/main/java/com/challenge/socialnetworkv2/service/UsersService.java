package com.challenge.socialnetworkv2.service;

import com.challenge.socialnetworkv2.data.dto.UserDto;
import com.challenge.socialnetworkv2.data.entity.Users;
import com.challenge.socialnetworkv2.data.enumeration.Role;

import java.util.List;

public interface UsersService {
    void enableAppUser(String email, Role role);

    void registerUser(UserDto userDto);

    Users findUserById(Long id);

    Users findUserByEmail(String email);

    List<UserDto> findAllUsers();

    List<UserDto> findAllFriends(Long id);
}
