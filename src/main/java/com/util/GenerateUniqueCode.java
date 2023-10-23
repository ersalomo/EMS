package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateUniqueCode {

    public static String generateUniqueCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        return sdf.format(new Date(System.currentTimeMillis()));
    }
}
