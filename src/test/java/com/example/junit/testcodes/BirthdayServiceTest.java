package com.example.junit.testcodes;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
This class experiments with junit jupiter assertions.
*/
class BirthdayServiceTest {
    BasicBirthdayService birthdayService = new BasicBirthdayService();

    @Test
    void getBirthDOW() {
        String dow = birthdayService.getBirthDOW(LocalDate.of(1979, 7, 14));
        assertEquals("SATURDAY", dow);
        dow = birthdayService.getBirthDOW(LocalDate.of(1979, 7, 15));
        assertEquals("SUNDAY", dow);
    }

    @Test
    void getChineseZodiac() {
        String dow = birthdayService.getChineseZodiac(LocalDate.of(1979, 7, 14));
        assertEquals("Sheep", dow);
    }


    @Test
    void getStarSign() {
        String dow = birthdayService.getStarSign(LocalDate.of(1979, 7, 14));
        assertEquals("Cancer", dow);
    }

    @Test
    void getValidBirthdayTest() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> birthdayService.getValidBirthday(null));
        String expectedMessage = "Must include birthday";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


}