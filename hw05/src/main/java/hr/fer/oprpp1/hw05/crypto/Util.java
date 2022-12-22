package hr.fer.oprpp1.hw05.crypto;

import java.util.Locale;

public class Util {

    public static byte[] hextobyte(String keyText) {
        if(keyText.length() == 0) return new byte[0];
        if(keyText.length() % 2 != 0) throw new IllegalArgumentException("keyText has to have even number of characters");

        byte[] bytearray = new byte[keyText.length() / 2];

        for(int i = 0; i < keyText.length() / 2; i++) {
            int first, second;
            String firstChar = String.valueOf(keyText.charAt(i * 2));
            String secondChar = String.valueOf(keyText.charAt(i * 2 + 1));
            switch(firstChar.toLowerCase(Locale.ROOT)) {
                case "a" -> first = 10;
                case "b" -> first = 11;
                case "c" -> first = 12;
                case "d" -> first = 13;
                case "e" -> first = 14;
                case "f" -> first = 15;
                default -> {
                    try {
                        first = Integer.parseInt(firstChar);
                    } catch (NumberFormatException   e) {
                        throw new IllegalArgumentException("String contains illegal character");
                    }
                }

            }
            switch(secondChar.toLowerCase(Locale.ROOT)) {
                case "a" -> second = 10;
                case "b" -> second = 11;
                case "c" -> second = 12;
                case "d" -> second = 13;
                case "e" -> second = 14;
                case "f" -> second = 15;
                default -> {
                    try {
                        second = Integer.parseInt(secondChar);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("String contains illegal character");
                    }
                }
            }

            int result = first * 16 + second;
            if(result > 255) throw new IllegalArgumentException("String contains illegal characters");
            
            if(result > 127) result = -(256 - result);
            
            bytearray[i] = ((byte) result);
        }

        return bytearray;
    }

    public static String bytetohex(byte[] bytearray) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytearray) {
            int intVal = b > 0 ? b : 256 + b;

            switch (intVal / 16) {
                case 10 -> builder.append("a");
                case 11 -> builder.append("b");
                case 12 -> builder.append("c");
                case 13 -> builder.append("d");
                case 14 -> builder.append("e");
                case 15 -> builder.append("f");
                default -> builder.append(intVal / 16);
            }
            switch (intVal % 16) {
                case 10 -> builder.append("a");
                case 11 -> builder.append("b");
                case 12 -> builder.append("c");
                case 13 -> builder.append("d");
                case 14 -> builder.append("e");
                case 15 -> builder.append("f");
                default -> builder.append(intVal % 16);
            }
        }
        return builder.toString();
    }
}
