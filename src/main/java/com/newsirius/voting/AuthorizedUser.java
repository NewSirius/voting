package com.newsirius.voting;

public class AuthorizedUser {
    private static int id = 1000;

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }
}