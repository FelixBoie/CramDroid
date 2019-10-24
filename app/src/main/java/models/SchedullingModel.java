package models;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import classes.timePoint;

public class SchedullingModel {

    private boolean usePercentileSpacing = true; // if false use constant spacing, if true use percentile spacing


    private timePoint lastStudied  = new timePoint(2000,1,1,1,0,0,0); // just initialize
    private timePoint finalTest  = new timePoint(2019,10,28,22, 0,0,0); // day of the final test

    private int blockedTimeStart  = 20; // the hour of the day
    private int blockedTimeStop = 8;    // the hour of the day

    private int numberOfTestsLeft = 0; //getNumberOfTestsLeft();
    SharedPreferences generalPref; // needed to save the amount of study times left

    public SchedullingModel() {
        updateLastTest();
    }


    // SWITCH HERE BETWEEN CONTANT AND PERCENTILE SCHEDULLING

    public int getSchedullingInHours(Context context){
        getNumberOfTestsLeft(context);
        updateLastTest();

        // if no more learning sessions left
        if(numberOfTestsLeft<=0){
            return -1;
        } else if(numberOfTestsLeft == 1) {
            // should only test once/ should test 24 h before the final test,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime tmp_lastStudied = LocalDateTime.of(lastStudied.year, Month.of(lastStudied.month), lastStudied.day, lastStudied.hour, 0);
                LocalDateTime tmP_finalStudy = LocalDateTime.of(finalTest.year, finalTest.month, finalTest.day, finalTest.hour, 0);

                Duration duration = Duration.between(tmp_lastStudied, tmP_finalStudy);

                if (duration.toHours() < 24) {
                    // then study now
                    return 0;
                } else {
                    // then study 24 hours before final test
                    return (int) (duration.toHours() - 24);
                }
            }
        }else {
            // DECIDE HERE BETWEEEN PERCENTIL OR CONSTANT SPACING
            int hoursTillNextSchedule = 0;
            if(usePercentileSpacing){
                hoursTillNextSchedule = getNextMessageInXHours_percentileSpacing();
            } else {
                hoursTillNextSchedule = getNextMessageInXHours_byConstantSpacing();
            }
            return adjustSentMessageInXHours(hoursTillNextSchedule);


        }
        return -1;
    }


    public void reduceNumberOfTestsLeft(Context context){
        //reduce the number of Tests left
        SharedPreferences.Editor editor = generalPref.edit();
        numberOfTestsLeft = numberOfTestsLeft-1;
        editor.putString("timesLeftToStudy",String.valueOf(numberOfTestsLeft));
        editor.apply();
    }



    // schedule based on PERCENTILE spacing
    private int getNextMessageInXHours_percentileSpacing() {
        int nextMessageInXHours = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // set times to LocalDateTime
            LocalDateTime tmp_lastStudied = LocalDateTime.of(lastStudied.year, Month.of(lastStudied.month), lastStudied.day, lastStudied.hour, 0);
            LocalDateTime tmP_finalStudy = LocalDateTime.of(finalTest.year,finalTest.month,finalTest.day,finalTest.hour,0);

            Duration duration = Duration.between(tmp_lastStudied, tmP_finalStudy);

            int difference_hours = (int) (duration.getSeconds()/(60*60));
            nextMessageInXHours = (int) (difference_hours * 0.4); // used 40%, as we only look for one week
        }
        return nextMessageInXHours;
    }

    // schedule based on CONSTANT spacing
    private int getNextMessageInXHours_byConstantSpacing() {
        int nextMessageInXHours = 0;

        if (numberOfTestsLeft == 0) {
            // should not test any more
            return -1;

        }else if(numberOfTestsLeft == 1) {
            // should only test once/ should test 24 h before the final test,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime tmp_lastStudied = LocalDateTime.of(lastStudied.year, Month.of(lastStudied.month), lastStudied.day, lastStudied.hour, 0);
                LocalDateTime tmP_finalStudy = LocalDateTime.of(finalTest.year, finalTest.month, finalTest.day, finalTest.hour, 0);

                Duration duration = Duration.between(tmp_lastStudied, tmP_finalStudy);

                if (duration.toHours() < 24){
                    // then study now
                    return 0;
                } else {
                    // then study 24 hours before final test
                    return (int) (duration.toHours()-24);
                }
            }

        }else {
            // needs to test multiple times
        }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // set times to LocalDateTime
                LocalDateTime tmp_lastStudied = LocalDateTime.of(lastStudied.year, Month.of(lastStudied.month), lastStudied.day, lastStudied.hour, 0);
                LocalDateTime tmP_finalStudy = LocalDateTime.of(finalTest.year, finalTest.month, finalTest.day, finalTest.hour, 0);

                Duration duration = Duration.between(tmp_lastStudied, tmP_finalStudy);

                int difference_hours = (int) (duration.getSeconds() / (60 * 60));
                nextMessageInXHours = (int) ((difference_hours - 24) / (numberOfTestsLeft)); // numberOfTestLeft ==1 would lead to an error
            }
        return nextMessageInXHours;
    }

    public void getNumberOfTestsLeft(Context context){

        generalPref = context.getSharedPreferences("timesLeftToStudy",Context.MODE_PRIVATE);
        String timesLeftToStudyString = generalPref.getString("timesLeftToStudy","4"); // in first part specify the key
        this.numberOfTestsLeft = Integer.parseInt(timesLeftToStudyString);
        System.out.println("getNumberOfTestsLeft: " + numberOfTestsLeft);

    }


    private void updateLastTest() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate nowDate = LocalDate.now();
            lastStudied.year = (int) nowDate.getYear();
            lastStudied.month = (int) nowDate.getMonthValue();
            lastStudied.day = (int) nowDate.getDayOfMonth();

            LocalTime nowTime = LocalTime.now();
            lastStudied.hour = nowTime.getHour();
        }
    }


    // adjust the time, to not sent notification outside the selected ours
    private int adjustSentMessageInXHours(int suggestedHours_bySpacingModel) {
        // if time in past
        if (suggestedHours_bySpacingModel<=0){
            return -1;
        }
        // check if time is blocked
        int suggestedTime_hours = (lastStudied.hour + suggestedHours_bySpacingModel )% 24;
        if(suggestedTime_hours < blockedTimeStop) {
            // to early in the day
            suggestedHours_bySpacingModel = suggestedHours_bySpacingModel + (blockedTimeStop - suggestedTime_hours);
        } else if(suggestedTime_hours > blockedTimeStart) {
            suggestedHours_bySpacingModel  = suggestedHours_bySpacingModel + (24-suggestedTime_hours) + blockedTimeStop;

        }
        return suggestedHours_bySpacingModel;
    }
}
