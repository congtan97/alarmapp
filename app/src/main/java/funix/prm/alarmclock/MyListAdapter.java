package funix.prm.alarmclock;
import android.app.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class MyListAdapter extends ArrayAdapter<TimeModel> {

    private final Activity context;
    private final ArrayList<TimeModel> timesList;

    AlarmDatabase alarmDatabase;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    ArrayList<PendingIntent> pendingList;

    public MyListAdapter(Activity context, ArrayList<TimeModel> timesList) {
        super(context, R.layout.mylist, timesList);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.timesList = timesList;
        alarmDatabase = new AlarmDatabase(context);


    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        ToggleButton toggleButton = (ToggleButton) rowView.findViewById(R.id.toggleButton);
        Button editButton = (Button) rowView.findViewById(R.id.editButton);
        Button deleteButton = (Button) rowView.findViewById(R.id.deleteButton);

        titleText.setText(timesList.get(position).toString());
        subtitleText.setText("Alarm Clock");

        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        pendingList = new ArrayList<>();
        for (int i = 0; i < timesList.size(); i++) {
            pendingList.add(null);
        }

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((ToggleButton) v).isChecked();
                if (checked){
                    // Your code
                    Toast.makeText(context, "You choose ON", Toast.LENGTH_SHORT).show();

                    calendar.set(Calendar.HOUR_OF_DAY, timesList.get(position).getHour());
                    calendar.set(Calendar.MINUTE, timesList.get(position).getMinute());

                    alarmIntent.putExtra("extra", "on");
                    pendingIntent = PendingIntent.getBroadcast(
                            context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT
                    );

                    pendingList.set(position, pendingIntent);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingList.get(position));
                }
                else{
                    // Your code
                    Toast.makeText(context, "You choose OFF", Toast.LENGTH_SHORT).show();
                    alarmManager.cancel(pendingIntent);
                    alarmIntent.putExtra("extra", "off");
                    context.sendBroadcast(alarmIntent);
                }
            }
        });

        Intent editIntent = new Intent(context, TimeEditorActivity.class);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editIntent.putExtra("position", position);
                context.startActivity(editIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmDatabase.deleteTime(timesList.get(position));
                timesList.remove(position);
                context.recreate();
            }
        });

        return rowView;

    };
}
