package com.challenge.socialnetworkv2.service.impl;

import com.challenge.socialnetworkv2.data.dto.UserDto;
import com.challenge.socialnetworkv2.data.entity.Users;
import com.challenge.socialnetworkv2.data.enumeration.Role;
import com.challenge.socialnetworkv2.data.repository.UsersRepository;
import com.challenge.socialnetworkv2.exception.UserNotFoundException;
import com.challenge.socialnetworkv2.service.FriendshipService;
import com.challenge.socialnetworkv2.service.UsersService;
import com.challenge.socialnetworkv2.validation.PictureValidator;
import com.challenge.socialnetworkv2.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserDetailsService, UsersService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendshipService friendshipService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, username)));
    }

    @Override
    public void enableAppUser(String email, Role role) {
        usersRepository.enableUsers(email, role);
    }

    @Override
    public void registerUser(UserDto userDto) {
        UserValidation.isExistUser(userDto.getEmail());
        PictureValidator.isValidImageSize(userDto.getPersonalPhoto());
        Users user = new Users();
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.REGISTERED_USER);
        usersRepository.save(user);
    }

    @Override
    public Users findUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found!"));
    }

    @Override
    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("This email does not exist!"));
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllFriends(Long id) {
        List<Users> users = friendshipService.getFriends(id);
        return users.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(Users user){
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstname());
        userDto.setLastName(user.getLastname());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
