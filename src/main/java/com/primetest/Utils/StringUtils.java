package com.primetest.Utils;

public class StringUtils {

    public static String replaceComma(String text){
        if (text!=null && text.contains(",")){
            return text.replaceAll(",", " ");
        }
        return text;
    }
}
