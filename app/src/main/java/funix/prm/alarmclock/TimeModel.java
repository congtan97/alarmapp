package funix.prm.alarmclock;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TimeModel implements Serializable {
    private int id;
    private int hour;
    private int minute;

    public TimeModel() {

    }

    public TimeModel(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public TimeModel(int id, int hour, int minute) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @NonNull
    @Override
    public String toString() {
        return this.hour + ":" + this.minute;
    }
}
