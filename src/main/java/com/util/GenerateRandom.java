package com.util;

import java.util.Random;

public class GenerateRandom {
    private static String number = "0123456789";
    private static String specials = "!@#$%&";
    private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static Random rnd = new Random();

    public static String getRandom(int len) {
        return generateRandom(len, false, false);
    }

    public static String getRandom(int len, boolean includeCharacter) {
        return generateRandom(len, includeCharacter, false);
    }

    public static String getRandom(int len, boolean includeCharacter, boolean isSpecialChar) {
        return generateRandom(len, includeCharacter, isSpecialChar);
    }

    private static String generateRandom(int len, boolean includeCharacter, boolean isSpecialChar) {
        String characters = number;

        if (includeCharacter)
            characters += chars;

        if (isSpecialChar)
            characters += specials;

        if (len > characters.length())
            len = characters.length();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++)
            sb.append(characters.charAt(rnd.nextInt(characters.length())));

        return sb.toString();
    }

    public static String getRandom() {
        int max = 10000, min = 99999;
        int random = min + (int) (Math.random() * ((max - min) + 1));

        return String.valueOf(random);
    }
}
