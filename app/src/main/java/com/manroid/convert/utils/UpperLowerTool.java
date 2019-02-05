package com.manroid.convert.utils;

/**
 * Created by Manroid
 */

public class UpperLowerTool {
    public static void main(String[] args) {
        String inp = "adgasdasd";
        System.out.println(inp);
        System.out.println(upperText(inp));
        System.out.println(lowerText(inp));
    }
    public static String upperText(String text) {
        return text.toUpperCase();
    }

    public static String lowerText(String text) {
        return text.toLowerCase();
    }
}
