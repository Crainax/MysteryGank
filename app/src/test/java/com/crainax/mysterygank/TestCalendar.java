package com.crainax.mysterygank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by crainax on 2016/10/15.
 */

public class TestCalendar {

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = format.parse("2016-05-16");
        Date date2 = format.parse("2016-05-17");

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        System.out.println("calendar1.getTime() = " + calendar1.getTime());
        System.out.println("calendar2.getTime() = " + calendar2.getTime());

    }

}
