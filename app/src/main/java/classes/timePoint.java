package classes;


import java.time.LocalDateTime;

public class timePoint {
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public int seconds;
    public int nanoSeconds;
    public timePoint(int year, int month, int day, int hour, int minute, int seconds, int nanoSeconds){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;
        this.nanoSeconds = nanoSeconds;

    }

    public void addHours(int addedHours){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime tmp_dateTime = LocalDateTime.of(this.year, this.month, this.day, this.hour, this.minute);
            tmp_dateTime.plusHours(addedHours);
            this.year = tmp_dateTime.getYear();
            this.month = tmp_dateTime.getMonthValue();
            this.day = tmp_dateTime.getDayOfMonth();
            this.hour = tmp_dateTime.getHour();
            this.minute = tmp_dateTime.getMinute();
            this.seconds = tmp_dateTime.getSecond();
            this.nanoSeconds = tmp_dateTime.getNano();
        }
    }

    public String toString(){//overriding the toString() method
        return year+"/"+month+"/"+day+" "+hour+":00";
    }
    /*
    public void addDays(int addedDays){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime tmp_dateTime = LocalDateTime.of(this.year, this.month, this.day, this.hour, 0);
            tmp_dateTime.plusDays(addedDays);
            this.year = tmp_dateTime.getYear();
            this.month = tmp_dateTime.getMonthValue();
            this.day = tmp_dateTime.getDayOfMonth();
            this.hour = tmp_dateTime.getHour();
        }
    }
    */

}
