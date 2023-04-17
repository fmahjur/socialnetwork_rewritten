package com.challenge.socialnetworkv2.service;

import com.challenge.socialnetworkv2.data.dto.UserDto;
import com.challenge.socialnetworkv2.data.entity.Users;

import java.util.List;

public interface FriendshipService {
    void newFriendRequest(Long id, UserDto friend);
    void acceptedFriendship(Long id, UserDto friend);
    void rejectedFriendship(Long id, UserDto friend);
    List<Users> getFriends(Long id);
}
