package com.annahockeyleague.comcom.utils;

import com.google.gson.JsonArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;

public class CommonUtils {

    Date now = new Date(); // java.util.Date, NOT java.sql.Date or java.sql.Timestamp!

    private final String currentDay = new SimpleDateFormat("d", Locale.ENGLISH).format(now);

    private final String currentMonth = new SimpleDateFormat("MMM", Locale.ENGLISH).format(now);

    public String getCurrentDay() {
        return currentDay;
    }
    public String getCurrentMonth(){
        return  currentMonth;
    }

    public String compareDayMonth(JsonArray data, int index){


        try {
            String currentDay = this.currentDay;
            String currentMonth = this.currentMonth;

            String createdAt = data.get(index).getAsJsonObject().get("createdAt").getAsString();
            SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            myDate.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            Date newDate = myDate.parse(createdAt);
            /*Date simpleCreatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(createdAt);

            DateFormat dateFormat = (DateFormat) simpleCreatedAt.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

            String createdAt = data.get(index).getAsJsonObject().get("createdAt").getAsString();
            DateForma simpleCreatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(createdAt);*/



            String month = new SimpleDateFormat("MMM").format(newDate);
            String day = new SimpleDateFormat("d").format(newDate);
            String time = new SimpleDateFormat("HH:mm").format(newDate);
            //currentMonth = "May"; currentDay="9";
            if(month.equals(currentMonth) && day.equals(currentDay))
                return time;
            return month+" "+day;
        }catch (Exception e){
            System.out.println(e);
        }
        return "null";
    }
}
