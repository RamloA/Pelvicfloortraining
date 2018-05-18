package com.example.pelvicfloortraining;

import android.arch.persistence.room.Entity;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class TrainingLog {

    public TrainingLog(String dato, int max) {
        this.dato = dato;
        this.max = max;
    }

    public TrainingLog(){
        dato="";
        max=0;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String dato;
    private int max;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getDato() {
        return dato;
    }

    public void setDato(String date) {
        this.dato = date;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}