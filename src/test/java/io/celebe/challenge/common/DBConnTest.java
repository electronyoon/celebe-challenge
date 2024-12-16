package io.celebe.challenge.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class DBConnTest {
    @Autowired
    private DataSource dataSource;

    @Test
    void connectionTest() throws SQLException {
        assertDoesNotThrow(() -> {
            Connection conn = dataSource.getConnection();
            conn.close();
        });
    }

}
