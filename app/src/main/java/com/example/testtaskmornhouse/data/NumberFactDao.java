package com.example.testtaskmornhouse.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NumberFactDao {
    @Query("SELECT MAX(id) FROM number_fact")
    LiveData<Integer> getMaxId();

    @Query("SELECT * FROM number_fact")
    LiveData<List<NumberFact>> loadAll();

    @Insert
    void insert(NumberFact numberFact);

    @Update
    void update(NumberFact numberFact);

    @Delete
    void delete(NumberFact numberFact);
}
