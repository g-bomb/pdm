package pt.ipbeja.poluent_beach.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


import pt.ipbeja.poluent_beach.data.Report;

@Dao
public interface ReportDao {

    @Query("select * from report")
    List<Report> getAll();

    @Query("select * from report where id = :id")
    Report get(long id);

    @Insert
    long insert(Report report);

    @Update
    void update(Report updatedReport);

    @Delete
     void delete(Report Report);
}
