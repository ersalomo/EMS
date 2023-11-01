package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GenerateUniqueCode {

    public static String generateUniqueCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = sdf.format(new Date(System.currentTimeMillis()));
        int randomValue = 1000 + new Random().nextInt(9000);
        return timestamp + randomValue;
    }
}
