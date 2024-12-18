package io.celebe.challenge.user.service;

import io.celebe.challenge.user.domain.User;
import io.celebe.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getProfile(String publicId) {
        User user = userRepository.findByPublicId(publicId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
        }
        if (!user.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "비활성화된 계정입니다.");
        }
        return user;
    }
}
