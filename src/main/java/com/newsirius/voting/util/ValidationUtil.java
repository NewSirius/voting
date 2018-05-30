package com.newsirius.voting.util;

import com.newsirius.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ValidationUtil {

    public static final LocalDateTime START_DATETIME_FOR_VOTE = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
    public static final LocalDateTime END_DATETIME_FOR_VOTE = LocalDateTime.of(LocalDate.now(), LocalTime.of(11,0,0));
    private static final LocalTime END_TIME_FOR_VOTE = END_DATETIME_FOR_VOTE.toLocalTime();

    public static boolean checkVoteTime(LocalDateTime localDateTime)   {
       return localDateTime.toLocalDate().isEqual(LocalDate.now()) && localDateTime.toLocalTime().isBefore(END_TIME_FOR_VOTE);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
}
