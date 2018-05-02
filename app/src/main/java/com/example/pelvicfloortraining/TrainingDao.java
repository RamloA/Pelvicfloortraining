package com.example.pelvicfloortraining;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by Ramlo on 02/05/2018.
 */

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
