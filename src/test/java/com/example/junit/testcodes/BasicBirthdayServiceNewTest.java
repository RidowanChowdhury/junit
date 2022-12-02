package com.example.junit.testcodes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/*
This class experiments with assertj.
*/
@ContextConfiguration(classes = {BasicBirthdayService.class})
class BasicBirthdayServiceNewTest {
    BasicBirthdayService birthdayService = new BasicBirthdayService();

    @BeforeEach
    void setup() {
        System.out.println("Before each was called!!");
    }

    @AfterEach
    void close() {
        System.out.println("After each was called!!");
    }

    @Test
    void getValidBirthday() {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> birthdayService.getValidBirthday(null));
    }

    @Test
    @DisplayName("Day of week from birthday")
    void getBirthDOW() {
        String dow = birthdayService.getBirthDOW(LocalDate.of(1979, 7, 14));
        assertThat("SATURDAY").isEqualTo(dow);
        dow = birthdayService.getBirthDOW(LocalDate.of(1979, 7, 15));
        assertThat("SUNDAY").isEqualTo(dow);
    }

    @Test
    void getChineseZodiac() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"Cancer"})
    void getStarSign(String star) {
        String sign = birthdayService.getStarSign(LocalDate.of(1979, 7, 14));
        assertThat(sign).isEqualTo(star);
    }


}