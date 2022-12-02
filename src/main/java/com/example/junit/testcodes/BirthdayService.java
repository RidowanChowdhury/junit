package com.example.junit.testcodes;

import java.time.LocalDate;

public interface BirthdayService {

    LocalDate getValidBirthday(String birthdayString) ;

    String getBirthDOW(LocalDate birthday);

    String getChineseZodiac(LocalDate birthday);

    String getStarSign(LocalDate birthday) ;
}
