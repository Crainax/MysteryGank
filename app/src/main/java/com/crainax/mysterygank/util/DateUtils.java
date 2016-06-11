package com.crainax.mysterygank.util;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank.util <br/>
 * Description: Handling the Date associating data.<br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/6/11 <br/>
 */
public class DateUtils {

    /**
     * Format example: 2016/06/11
     */
    public static final String FORMAT_YMD = "yyyy/MM/dd";

    public static String formatDate(Date date, String format) {

        return (String) DateFormat.format(format, date);

    }
}
