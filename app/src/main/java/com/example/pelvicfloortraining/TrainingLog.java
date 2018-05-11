package com.example.pelvicfloortraining;

import android.arch.persistence.room.Entity;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class TrainingLog {

    public TrainingLog(String dato, String type, String max) {
        this.dato = dato;
        this.type = type;
        this.max = max;
    }

    public TrainingLog(){
        dato="";
        type="";
        max="";
    }

    @PrimaryKey(autoGenerate = true) @NonNull
    //private int id;
    private String dato;
    private String type;
    private String max;

    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getDato() {
        return dato;
    }

    public void setDato(String date) {
        this.dato = dato;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}