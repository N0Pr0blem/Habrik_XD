package com.example.article_service.util;

import com.ibm.icu.text.Transliterator;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DiffUtils {

    public static Date getCurrentMoscowTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        return calendar.getTime();
    }

    public static String toSlug(String input) {
        if (input == null) {
            return "";
        }
        Transliterator toLatinTrans = Transliterator.getInstance("Cyrillic-Latin");
        final String transliterated = toLatinTrans.transliterate(input);
        String lowerCased = transliterated.toLowerCase(Locale.ROOT);
        String cleaned = lowerCased.replaceAll("[^a-z0-9\\s-]", "");
        return cleaned.trim().replaceAll("[\\s-]+", "-");
    }
}
