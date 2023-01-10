package com.github.vbomm.gpt3discordchatbot;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCreator {

    public String createMD5Hash(final String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());

        return convertToHex(messageDigest);
    }

    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);

        while (hexText.length() < 32)
            hexText = "0".concat(hexText);

        return hexText;
    }
}
