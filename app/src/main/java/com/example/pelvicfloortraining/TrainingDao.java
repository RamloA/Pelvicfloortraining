package com.example.pelvicfloortraining;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


@Dao
public interface TrainingDao {

    @Query("SELECT * FROM `traininglog`")
    TrainingLog getLog();

    @Update
    int update(TrainingLog trainingLog);

    @Insert
    void insert(TrainingLog trainingLog);

    @Query("SELECT COUNT(*) from `traininglog`")
    int counttraininglog();

}
