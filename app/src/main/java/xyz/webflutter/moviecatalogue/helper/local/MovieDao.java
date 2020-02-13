package xyz.webflutter.moviecatalogue.helper.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import xyz.webflutter.moviecatalogue.models.ResultMovie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(ResultMovie movies);

    @Query("SELECT * FROM tbMovie")
    ResultMovie[] readMovies();

    @Update
    int updateMovie(ResultMovie models);

    @Delete
    void deleteMovie(ResultMovie models);

    @Query("SELECT * FROM tbMovie WHERE id = :id LIMIT 1")
    ResultMovie selectDetailMovie(String id);
}
