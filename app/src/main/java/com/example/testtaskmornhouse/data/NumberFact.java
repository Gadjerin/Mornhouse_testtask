package com.example.testtaskmornhouse.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "number_fact")
public class NumberFact {
    public NumberFact(){}

    public NumberFact(int number, String fact) {
        this.number = number;
        this.fact = fact;
    }

    @NonNull
    @Override
    public String toString() {
        return number + " " + fact;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public int number;

    @ColumnInfo
    public String fact;
}
