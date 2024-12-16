package io.celebe.challenge.common;

import io.celebe.challenge.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicIdGenService {
    private final PublicIdGenerator generator;
    private final UserMapper userMapper;

    public String generateUniquePublicId() {
        String publicId;
        do {
            publicId = generator.generate();
        } while (userMapper.existsByPublicId(publicId));

        return publicId;
    }
}
