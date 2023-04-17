package com.challenge.socialnetworkv2.service.impl;

import com.challenge.socialnetworkv2.data.dto.UserDto;
import com.challenge.socialnetworkv2.data.entity.Friendship;
import com.challenge.socialnetworkv2.data.entity.Users;
import com.challenge.socialnetworkv2.data.enumeration.FriendshipStatus;
import com.challenge.socialnetworkv2.data.repository.FriendshipRepository;
import com.challenge.socialnetworkv2.exception.FriendshipNotFoundException;
import com.challenge.socialnetworkv2.service.FriendshipService;
import com.challenge.socialnetworkv2.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;

    private final UsersService usersService;

    @Override
    public void newFriendRequest(Long id, UserDto friend) {
        Users firstUser = usersService.findUserById(id);
        Users secondUser = usersService.findUserByEmail(friend.getEmail());

        Friendship friendship = friendshipRepository.checkingFriendShip(firstUser, secondUser).orElse(null);

        if (friendship == null) {
            friendship = new Friendship();
            friendship.setFirstUser(firstUser);
            friendship.setSecondUser(secondUser);
            friendship.setFriendshipStatus(FriendshipStatus.REQUESTED);
            friendshipRepository.save(friendship);
        } else if (friendship.getFirstUser().equals(firstUser) & friendship.getFriendshipStatus().equals(FriendshipStatus.ACCEPTED)) {
            friendshipRepository.updateFriendShipStatus(firstUser, secondUser, FriendshipStatus.MUTUAL);
        }
    }

    @Override
    public void acceptedFriendship(Long id, UserDto friend) {
        Users firstUser = usersService.findUserById(id);
        Users secondUser = usersService.findUserByEmail(friend.getEmail());
        friendshipRepository.updateFriendShipStatus(firstUser, secondUser, FriendshipStatus.ACCEPTED);
    }

    @Override
    public void rejectedFriendship(Long id, UserDto friend) {
        Users firstUser = usersService.findUserById(id);
        Users secondUser = usersService.findUserByEmail(friend.getEmail());
        Friendship friendship = friendshipRepository.checkingFriendShip(firstUser, secondUser).orElseThrow(() -> new FriendshipNotFoundException("You are not a friend!"));
        friendshipRepository.delete(friendship);
    }

    @Override
    public List<Users> getFriends(Long id) {
        Users currentUser = usersService.findUserById(id);
        List<Friendship> friendsByFirstUser = friendshipRepository.findByFirstUser(currentUser);
        List<Friendship> friendsBySecondUser = friendshipRepository.findBySecondUser(currentUser);
        List<Users> friendUsers = new ArrayList<>();

        for (Friendship friend : friendsByFirstUser) {
            friendUsers.add(usersService.findUserById(friend.getSecondUser().getId()));
        }
        for (Friendship friend : friendsBySecondUser) {
            friendUsers.add(usersService.findUserById(friend.getFirstUser().getId()));
        }
        return friendUsers;
    }
}
