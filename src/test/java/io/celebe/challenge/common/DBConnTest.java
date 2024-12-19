package io.celebe.challenge.common;

import io.celebe.challenge.CelebeChallengeApplicationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class DBConnTest extends CelebeChallengeApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("DB 커넥션 확인")
    void connectionTest() throws SQLException {
        assertDoesNotThrow(() -> {
            Connection conn = dataSource.getConnection();
            conn.close();
        });
    }

}
