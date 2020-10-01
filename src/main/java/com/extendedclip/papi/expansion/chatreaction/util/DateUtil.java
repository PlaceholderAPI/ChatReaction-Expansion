package com.extendedclip.papi.expansion.chatreaction.util;

import me.clip.placeholderapi.PlaceholderAPIPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String formatDate(long time) {
        SimpleDateFormat format = PlaceholderAPIPlugin.getDateFormat();
        Date unixSeconds = new Date(time);
        return format.format(unixSeconds);
    }
}
