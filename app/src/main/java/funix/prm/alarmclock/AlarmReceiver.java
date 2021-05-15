package funix.prm.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver", "AlarmReceiver has been called.");
        String extra = intent.getExtras().getString("extra");
        Intent receiverIntent = new Intent(context, AlarmService.class);
        receiverIntent.putExtra("extra", extra);
        context.startService(receiverIntent);
    }
}
