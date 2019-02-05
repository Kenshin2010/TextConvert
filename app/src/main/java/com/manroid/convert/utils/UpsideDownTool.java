package com.manroid.convert.utils;

import static com.manroid.convert.utils.Style.FLIP;
import static com.manroid.convert.utils.Style.SUPPER_SCRIPT;
import static com.manroid.convert.utils.Style.NORMAL;


/**
 * Created by Manroid
 */

public class UpsideDownTool {

    /**
     * up side down text
     * <p>
     * hello world, i'm dennis -> sıuuǝp ɯ,ı 'p1ɹoʍ o11ǝɥ
     */
    public static String textToUpsideDown(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            result += (a != -1) ? FLIP.charAt(a) : letter;
        }
        return new StringBuilder(result).reverse().toString();
    }

    public static String upsideDownToText(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = FLIP.indexOf(letter);
            result += (a != -1) ? NORMAL.charAt(a) : letter;
        }
        return new StringBuilder(result).reverse().toString();
    }

    public static void main(String[] args) {
        String inp = "\"upside down\" will create the illusion of an upside down page/text via string reversal and character substitution for letters a to z.";
        System.out.println("Test case 1: ");
        System.out.println(inp);
        System.out.println(textToUpsideDown(inp));
        System.out.println(textToUpsideDown(textToUpsideDown(inp)));

        System.out.println("Test case 2: ");
        inp = "abcdefghijklmnopqrstuvwxyz'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        System.out.println(inp);
        System.out.println(textToUpsideDown(inp));
        System.out.println(textToUpsideDown(textToUpsideDown(inp)));
    }
}
