package com.firzen.personal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Query("SELECT * FROM Task ORDER BY year ASC, monthNum ASC, date ASC")
    LiveData<List<Task>> getAllTasks();

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);
}
