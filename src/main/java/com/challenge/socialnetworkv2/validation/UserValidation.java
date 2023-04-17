package com.challenge.socialnetworkv2.validation;

import com.challenge.socialnetworkv2.data.repository.UsersRepository;
import com.challenge.socialnetworkv2.exception.UserValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private static UsersRepository usersRepository;

    public static void isExistUser(String email) {
        if (usersRepository.existsByEmail(email))
            throw new UserValidationException("this Email is already Exist!");
    }
}
