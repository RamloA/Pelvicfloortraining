package com.example.pelvicfloortraining;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Waterfragment extends Fragment {
    ArrayList<Fluidintake> mfluidintakes = new ArrayList<>();
    public  View view;
    AppDatabase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frament_waterlevel, container, false);
        initBeans();
        return view;
    }
    private void initBeans() {
        //Error ses i logcat pga. vi ikke kalder den i onStart
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "Fluidintake")
                .allowMainThreadQueries()
                .build();
        if (db.fluidintakeDao().getAllFluidintake().size()!=0) {
            for (int i = 0; i < db.fluidintakeDao().getAllFluidintake().size(); i++) {
                Fluidintake fluidintake = db.fluidintakeDao().getAllFluidintake().get(i);
                mfluidintakes.add(fluidintake);
            }
            initAdapter();

        }
    }
    private void  initAdapter(){
        RecyclerView recyclerView = view.findViewById(R.id.Recyclerview_water);
        RecyclerviewAdapter_Water adapter = new RecyclerviewAdapter_Water(getContext(), mfluidintakes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
