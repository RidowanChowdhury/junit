package com.example.junit.testcodes;

import java.time.LocalDate;
import java.util.Objects;

public class User {
    private String name;
    private Integer age;
    private Boolean blocked;
    private LocalDate birthDate;


    public User(String name, Integer age, Boolean blocked, LocalDate birthDate) {
        this.name = name;
        this.age = age;
        this.blocked = blocked;
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(age, user.age) && Objects.equals(blocked, user.blocked) && Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, blocked, birthDate);
    }
}
