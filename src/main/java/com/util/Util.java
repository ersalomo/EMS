package com.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@Component
@Slf4j
public class Util {

    public static String toSnakeCaseForm(String field) {
        return field.replaceAll("([a-z])([A-Z])","$1_$2").toLowerCase();
    }

    public static boolean trueOrFalse(String value) {
        if (Objects.isNull(value)) return false;
        return
                value.equalsIgnoreCase("1") ||
                value.equalsIgnoreCase("true");
    }

    public static Date getCurrentDate() {
        try {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);
            return df.parse(df.format(new Date()));
        }catch (ParseException e) {
            log.error(e.getMessage());
            return new Date();
        }
    }
    public static double calculateTotal(int count, double unitPrice) {
        return count * unitPrice;
    }
}
