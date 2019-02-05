package com.manroid.convert.utils;

import static com.manroid.convert.utils.Style.SUB_SCRIPT;
import static com.manroid.convert.utils.Style.NORMAL;


/**
 * Created by Manroid
 */

public class SubScriptText {
    public static String textToSub(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            result += (a != -1) ? SUB_SCRIPT.charAt(a) : letter;
        }
        return result;
    }

    public static String subToText(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = SUB_SCRIPT.indexOf(letter);
            result += (a != -1) ? NORMAL.charAt(a) : letter;
        }
        return result;
    }

    public static void main(String[] args) {
        String x = textToSub("abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        System.out.println(x);
        System.out.println(subToText(x));
    }

}
