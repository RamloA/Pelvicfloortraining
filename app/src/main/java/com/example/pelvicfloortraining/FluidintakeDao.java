package com.example.pelvicfloortraining;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by Ramlo on 12/05/2018.
 */

public interface FluidintakeDao {

    @Query("SELECT * FROM `Fluidintake`")
    Fluidintake getLog();

    @Insert
    void insertAll(Fluidintake fluidintake);
}
