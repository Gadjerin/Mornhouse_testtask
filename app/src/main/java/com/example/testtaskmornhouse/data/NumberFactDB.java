package com.example.testtaskmornhouse.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NumberFact.class}, version = 1)
public abstract class NumberFactDB extends RoomDatabase {
    public abstract NumberFactDao numberFactDao();
}
