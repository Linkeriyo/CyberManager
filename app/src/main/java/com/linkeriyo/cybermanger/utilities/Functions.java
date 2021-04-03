package com.linkeriyo.cybermanger.utilities;

public class Functions {

    public static boolean stringIsNullOrEmpty(final String string) {
        return string == null || string.equals("");
    }

    public static boolean isUserLoggedIn() {
        return Preferences.getToken() != null;
    }
}
