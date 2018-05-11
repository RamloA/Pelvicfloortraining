package com.example.pelvicfloortraining;

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

/**
 * Created by Ramlo on 02/05/2018.
 */

public class Waterfragment extends Fragment {
    ArrayList<String> mDate  = new ArrayList<>();
    public static View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frament_waterlevel, container, false);
        initBeans();
        return view;

    }
    private void initBeans(){
        //Error ses i logcat pga. vi ikke kalder den i onStart
        for (int i=0; i<10; i++){
            mDate.add("Hey"+i);
        }
        initAdapter();
    }
    private void  initAdapter(){
        RecyclerView recyclerView = view.findViewById(R.id.Recyclerview_water);
        RecyclerviewAdapter_Water adapter = new RecyclerviewAdapter_Water(getContext(), mDate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
