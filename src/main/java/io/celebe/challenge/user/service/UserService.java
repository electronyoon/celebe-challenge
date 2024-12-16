package io.celebe.challenge.user.service;

import io.celebe.challenge.model.User;
import io.celebe.challenge.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public User getProfile(String publicId) {
        User user = userMapper.selectUserByPublicId(publicId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!user.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is deactivated");
        }
        return user;
    }
}
