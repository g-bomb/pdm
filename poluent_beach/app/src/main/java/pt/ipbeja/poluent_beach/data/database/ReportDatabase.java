package pt.ipbeja.poluent_beach.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pt.ipbeja.poluent_beach.data.Report;

@Database(entities = {Report.class}, version = 1, exportSchema = false)
public abstract class ReportDatabase extends RoomDatabase {

    private static ReportDatabase instance;

    public static ReportDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room
                    .databaseBuilder(context, ReportDatabase.class, "report_db")
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract ReportDao reportDao();
}
