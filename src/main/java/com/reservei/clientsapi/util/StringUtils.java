package com.reservei.clientsapi.util;

public class StringUtils {

    public static String removeDotsAndDashes(String str) {
        return str.replaceAll("[^0-9]", "");
    }
}
