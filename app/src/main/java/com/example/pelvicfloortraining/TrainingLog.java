package com.example.pelvicfloortraining;

import android.arch.persistence.room.Entity;

import android.arch.persistence.room.PrimaryKey;



@Entity
public class TrainingLog {

    public TrainingLog(String dato, String type, int max) {
        this.dato = dato;
        this.type = type;
        this.max = max;
    }

    public TrainingLog(){
        dato="";
        type="";
        max=0;
    }
    @PrimaryKey
    private int id;
    private String dato;
    private String type;
    private int max;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}