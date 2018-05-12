package com.example.pelvicfloortraining;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Ramlo on 12/05/2018.
 */

@Database(entities = {Fluidintake.class}, version = 1)
public abstract class FluidintakeDatabe extends RoomDatabase {

    //public abstract FluidintakeDao fluidintakeDao();

    private static FluidintakeDatabe INSTANCE;

    public static FluidintakeDatabe getFluidintakeDatabe (Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), FluidintakeDatabe.class, "FluidintakeDatabse")
                            // allow queries on the main thread.
                            // Don't do this on a real app!
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }

}
