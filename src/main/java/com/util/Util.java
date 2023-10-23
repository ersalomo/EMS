package com.util;

public class Util {

    public static String toSnakeCaseForm(String field) {
        return field.replaceAll("([a-z])([A-Z])","$1_$2").toLowerCase();
    }
}
