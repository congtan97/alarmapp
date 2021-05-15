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

public class TimeEditorActivity extends AppCompatActivity {
    Button okBtn, cancelBtn;
    TimePicker timePicker;

    AlarmDatabase alarmDatabase = new AlarmDatabase(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeeditor);

        //Phan TimePicker
        timePicker = (TimePicker) findViewById(R.id.timePicker2);

        okBtn = (Button) findViewById(R.id.okButton);
        cancelBtn = (Button) findViewById(R.id.cancelButton2);
        Intent editIntent = getIntent();

        Intent i = new Intent(this, MainActivity.class);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                int position = editIntent.getExtras().getInt("position");
                //Edit database
                //1. Create new TimeModel has id is position+1
                TimeModel timeModel = new TimeModel(position + 1, hour, minute);
                //2. Update the time on the database
                alarmDatabase.updateTime(timeModel);
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
