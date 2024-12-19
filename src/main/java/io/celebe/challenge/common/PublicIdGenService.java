package io.celebe.challenge.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicIdGenService {
    private final PublicIdGenerator generator;

    public String generateUniquePublicId() {
        return generator.generate();
    }
}
