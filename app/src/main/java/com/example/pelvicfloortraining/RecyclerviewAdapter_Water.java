package com.example.pelvicfloortraining;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;



public class RecyclerviewAdapter_Water extends RecyclerView.Adapter<RecyclerviewAdapter_Water.ViewHolder>{
    private static final String TAG = "Adapter_Water";

    private ArrayList<String> mDate = new ArrayList<>();
    private Context mContext;

    public RecyclerviewAdapter_Water(Context mContext, ArrayList<String> mDate) {
        this.mDate = mDate;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listwaterinfo, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "OBViewHolder:called");
        holder.date.setText(mDate.get(position));
        holder.waterlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }

        });

    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       TextView date;
       RelativeLayout waterlayout;
        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            waterlayout = itemView.findViewById(R.id.water_layout);

        }
    }
}
