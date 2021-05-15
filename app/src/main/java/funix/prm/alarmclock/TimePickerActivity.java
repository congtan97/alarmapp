package funix.prm.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity {
    Button addBtn, cancelBtn;
    TimePicker timePicker;

    AlarmDatabase alarmDatabase = new AlarmDatabase(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);

        //Phan TimePicker
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        addBtn = (Button) findViewById(R.id.addButton);
        cancelBtn = (Button) findViewById(R.id.cancelButton);

        Intent i = new Intent(this, MainActivity.class);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                TimeModel timeModel = new TimeModel(hour, minute);
                alarmDatabase.addTime(timeModel);

                startActivity(i);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
