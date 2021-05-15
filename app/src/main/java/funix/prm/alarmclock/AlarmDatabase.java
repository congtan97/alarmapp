package funix.prm.alarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AlarmDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AlarmManager.db";
    private static final String TABLE_NAME = "Time";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_HOUR = "Hour";
    private static final String COLUMN_MINUTE = "Minute";

    public AlarmDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate...");
        // Script to create table.
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_HOUR + " INTERGER,"
                + COLUMN_MINUTE + " INTEGER" + ")";
        // Execute script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void addTime(TimeModel timeModel) {
        Log.i(TAG, "MyDatabaseHelper.addTime...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUR, timeModel.getHour());
        values.put(COLUMN_MINUTE, timeModel.getMinute());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);

        // Closing database connection
        db.close();
    }

    public TimeModel getTime(int id) {
        Log.i(TAG, "MyDatabaseHelper.getTime... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_HOUR, COLUMN_MINUTE}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TimeModel timeModel = new TimeModel(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)));
        // return Time
        return timeModel;
    }

    public List<TimeModel> getAllTimes() {
        Log.i(TAG, "MyDatabaseHelper.getAllTime ... " );

        List<TimeModel> timeList = new ArrayList<TimeModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TimeModel timeModel = new TimeModel();
                timeModel.setId(Integer.parseInt(cursor.getString(0)));
                timeModel.setHour(Integer.parseInt(cursor.getString(1)));
                timeModel.setMinute(Integer.parseInt(cursor.getString(2)));
                // Adding note to list
                timeList.add(timeModel);
            } while (cursor.moveToNext());
        }

        // return note list
        return timeList;
    }

    public int getTimesCount() {
        Log.i(TAG, "MyDatabaseHelper.getTimesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public int updateTime(TimeModel timeModel) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + timeModel.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUR, timeModel.getHour());
        values.put(COLUMN_MINUTE, timeModel.getMinute());

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(timeModel.getId())});
    }

    public void deleteTime(TimeModel timeModel) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + timeModel.getId() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(timeModel.getId()) });
        db.close();
    }

}
