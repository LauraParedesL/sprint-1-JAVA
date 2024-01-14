package com.mindhub.homebanking.utils;

public class AccountUtils {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String generateAccountNumber(){
        String number = "VIN" + getRandomNumber(10000000, 99999999);
        return number;
    }
}
