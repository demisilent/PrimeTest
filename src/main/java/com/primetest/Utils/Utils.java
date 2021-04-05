package com.primetest.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Utils {

    public static int generateID() {
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            return Integer.parseInt(String.format("%06d", number));
        }

    public static String converterDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(date);
    }

    public static String setRole(String login) {
        if (login.equals("admin")) {
            return "admin";
        } else
            return "user";
    }
}
