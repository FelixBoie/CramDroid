package models;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import classes.timePoint;


public class spacingModel {
    private timePoint lastStudied  = new timePoint(2000,1,1,1);
    private timePoint finalTest  = new timePoint(2019,10,11,12);

    private int blockedTimeStart  = 20; // the hour of the day
    private int blockedTimeStop = 8;    // the hour of the day

    public spacingModel() {
        updateLastTest();
    }

    public void updateLastTest() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate nowDate = LocalDate.now();
            lastStudied.year = (int) nowDate.getYear();
            lastStudied.month = (int) nowDate.getMonthValue();
            lastStudied.day = (int) nowDate.getDayOfMonth();

            LocalTime nowTime = LocalTime.now();
            lastStudied.hour = nowTime.getHour();
        }
    }

    public int getNextMessageInMS_test(){
        return 20000;
    }
    public int getNextMessageInXHours(){
        updateLastTest();
        return adjustSentMessageInXHours(getNextMessageInXHours_bySpacingModel());
    }

    public timePoint getNextMessageTimePoint(){
        timePoint nextMessageTimePoint;
        updateLastTest();
        nextMessageTimePoint = lastStudied;
        nextMessageTimePoint.addHours(getNextMessageInXHours());
        return nextMessageTimePoint;
    }

    // a simple version, only gives back a day
    int getNextMessageInXHours_bySpacingModel() {
        int nextMessageInXHours = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // set times to LocalDateTime
            LocalDateTime tmp_lastStudied = LocalDateTime.of(lastStudied.year, Month.of(lastStudied.month), lastStudied.day, lastStudied.hour, 0);
            LocalDateTime tmP_finalStudy = LocalDateTime.of(finalTest.year,finalTest.month,finalTest.day,finalTest.hour,0);

            Duration duration = Duration.between(tmp_lastStudied, tmP_finalStudy);

            int difference_hours = (int) (duration.getSeconds()/(60*60));
            nextMessageInXHours = (int) (difference_hours * 0.4);
        }
        //System.out.println("spacing model start:" + nextMessageInXHours);
        return nextMessageInXHours;
    }

    int adjustSentMessageInXHours(int suggestedHours_bySpacingModel) {
        // check if time is blocked
        int suggestedTime_hours = (lastStudied.hour + suggestedHours_bySpacingModel )% 24;
        if(suggestedTime_hours < blockedTimeStop) {
            // to early in the day
            suggestedHours_bySpacingModel = suggestedHours_bySpacingModel + (blockedTimeStop - suggestedTime_hours);
            //System.out.println("too early");
        } else if(suggestedTime_hours > blockedTimeStart) {
                suggestedHours_bySpacingModel  = suggestedHours_bySpacingModel + (24-suggestedTime_hours) + blockedTimeStart;
                //System.out.println("too late");

            }
        //System.out.println("spacing model adjusted:" + suggestedHours_bySpacingModel);
        return suggestedHours_bySpacingModel;
    }

    // set final test time
    public void finalTestTime(int year, int month, int day, int hour){
        finalTest.year =year;
        finalTest.month =month;
        finalTest.day =day;
        finalTest.hour =hour;
    }







}
