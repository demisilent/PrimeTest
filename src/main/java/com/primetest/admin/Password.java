package com.primetest.admin;

import com.primetest.database.DBLoadData;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Password {

    public static boolean checkPassword(String login, String password) {
        String securePassword = get_SHA_512_SecurePassword(password, login);
        String securePasswordDB = getUser(login).getPassword();
        return securePassword.equals(securePasswordDB);
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static Account getUser(String login) {
        return DBLoadData.getAccount(login);
    }

}