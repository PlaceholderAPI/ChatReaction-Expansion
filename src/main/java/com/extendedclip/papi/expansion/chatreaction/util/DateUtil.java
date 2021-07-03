package com.extendedclip.papi.expansion.chatreaction.util;

import me.clip.placeholderapi.PlaceholderAPIPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    public static String formatDate(final long time) {
        final SimpleDateFormat format = PlaceholderAPIPlugin.getDateFormat();
        final Date unixSeconds = new Date(time);
        return format.format(unixSeconds);
    }
}
