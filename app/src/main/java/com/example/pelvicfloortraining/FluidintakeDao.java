package com.example.pelvicfloortraining;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


public interface FluidintakeDao {

    @Query("SELECT * FROM `Fluidintake`")
    Fluidintake getLog();

    @Insert
    void insertAll(Fluidintake fluidintake);
}
