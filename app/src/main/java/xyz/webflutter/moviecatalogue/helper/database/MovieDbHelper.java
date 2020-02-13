package xyz.webflutter.moviecatalogue.helper.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import xyz.webflutter.moviecatalogue.helper.local.MovieDao;
import xyz.webflutter.moviecatalogue.models.ResultMovie;

@Database(entities = {ResultMovie.class}, version = 4)
public abstract class MovieDbHelper extends RoomDatabase {
    public abstract MovieDao movieDao();

    private static MovieDbHelper dbHelper;

    public static MovieDbHelper getDbHelper(Context context){
        synchronized (MovieDbHelper.class){
            if (dbHelper == null){
                dbHelper = Room.databaseBuilder(
                        context, MovieDbHelper.class,
                        "dbMovie"
                ).allowMainThreadQueries().build();
            }
        }return dbHelper;
    }
}
