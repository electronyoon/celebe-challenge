package io.celebe.challenge.common;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class PublicIdGenerator {
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    public String generate() {
        StringBuilder builder = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            builder.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return builder.toString();
    }
}
