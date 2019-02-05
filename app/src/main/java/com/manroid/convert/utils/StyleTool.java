package com.manroid.convert.utils;

import java.util.ArrayList;

import static com.manroid.convert.utils.Style.SIZE_OF_STYLE;
import static com.manroid.convert.utils.Style.NORMAL;
/**
 * Created by Manroid
 */

public class StyleTool {


    public static String convert(String text, int to) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            result += (a != -1) ? Style.get(a, to) : letter;
        }
        return result;
    }

    public static ArrayList<String> convert(String text) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < SIZE_OF_STYLE; i++) {
            arrayList.add(convert(text, i));
        }
        return arrayList;
    }

    public static void main(String[] args) {

    }
}
