package funix.prm.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addAlarmBtn;
    ListView listView;

    private AlarmDatabase alarmDatabase = new AlarmDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addAlarmBtn = (Button) findViewById(R.id.addAlarmButton);
        listView = (ListView) findViewById(R.id.listAlarm);

        ArrayList<TimeModel> timeList = new ArrayList<>(alarmDatabase.getAllTimes());

        MyListAdapter adapter = new MyListAdapter(this, timeList);
        listView.setAdapter(adapter);

        Intent i = new Intent(this, TimePickerActivity.class);
        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

    }
}