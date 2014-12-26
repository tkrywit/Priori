package com.priori.tkrywit.priori;

/**
 * Created by Thomas on 12/22/2014.
 *
 * Responsible for creating 2 letter text icon names from categories
 */
public final class CategoryHelper {

    public static String getAbbrevName(String name) {

        String aName = (name.substring(0,2));
        String[] words = name.split(" ");
        if (words.length > 1) {
            aName = (words[0].substring(0, 1) + words[1].substring(0, 1));
        }
        return aName.toUpperCase();
    }
}