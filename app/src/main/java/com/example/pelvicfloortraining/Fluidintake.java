package com.example.pelvicfloortraining;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ramlo on 12/05/2018.
 */

@Entity
public class Fluidintake {

    public Fluidintake(String date, String time, int fluidintake, int urination_level, String leakage) {
        this.date = date;
        this.time = time;
        this.fluidintake = fluidintake;
        this.urination_level = urination_level;
        this.leakage = leakage;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private String time;
    private int fluidintake;
    private int urination_level;
    private String leakage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFluidintake() {
        return fluidintake;
    }

    public void setFluidintake(int fluidintake) {
        this.fluidintake = fluidintake;
    }

    public int getUrination_level() {
        return urination_level;
    }

    public void setUrination_level(int urination_level) {
        this.urination_level = urination_level;
    }

    public String getLeakage() {
        return leakage;
    }

    public void setLeakage(String leakage) {
        this.leakage = leakage;
    }
}
