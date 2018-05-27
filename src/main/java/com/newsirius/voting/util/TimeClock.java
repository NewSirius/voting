package com.newsirius.voting.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TimeClock {

    private boolean fakeTime = false;

    public TimeClock() {
    }

    public LocalDateTime getLocalDateTime()    {

        if (!fakeTime)  {
            return LocalDateTime.now();
        } else
            return LocalDateTime.of(LocalDate.now(), LocalTime.of(10,5, 0));
    }

    public boolean isFakeTime() {
        return fakeTime;
    }

    public void setFakeTime(boolean fakeTime) {
        this.fakeTime = fakeTime;
    }
}
