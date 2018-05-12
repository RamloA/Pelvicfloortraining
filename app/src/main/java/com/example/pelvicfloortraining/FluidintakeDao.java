package com.example.pelvicfloortraining;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FluidintakeDao {

    @Query("SELECT * FROM `Fluidintake`")
    List<Fluidintake> getAllFluidintake();

    @Insert
    void insertAll(Fluidintake... fluidintake);

    //@Update
    //int update(Fluidintake fluidintake);
}
