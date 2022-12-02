package com.example.junit.testcodes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;


class UserTest {
    @Test
    void checkObjectAssert() {
        User user1 = new User("Ridwan", 29, false, LocalDate.of(1985, 5, 4));
        User user2 = new User("Ridwan", 29, false, LocalDate.of(1985, 5, 4));
        assertThat(user1).isEqualTo(user2);
    }
}