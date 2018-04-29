package com.example.pelvicfloortraining;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerviewAdapter";

    private ArrayList<String> mBeanAddress =new ArrayList<>();
    private ArrayList<String> mBean_SS = new ArrayList<>();
    private Context mContext;

    public RecyclerviewAdapter(Context mContext, ArrayList<String> mBeanAddress, ArrayList<String> mBean_SS) {
        this.mBeanAddress = mBeanAddress;
        this.mBean_SS = mBean_SS;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitems,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.BeanAddress.setText(mBeanAddress.get(position));
        holder.Bean_SS.setText(mBean_SS.get(position));
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on:"+mBeanAddress.get(position));
                Toast.makeText(mContext, mBeanAddress.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, Training.class);
                intent.putExtra("Bean_address", mBeanAddress.get(position));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mBean_SS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView BeanAddress;
        TextView Bean_SS;
        RelativeLayout parent_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            BeanAddress = itemView.findViewById(R.id.Bean_address);
            Bean_SS = itemView.findViewById(R.id.Bean_SS);
            parent_layout=itemView.findViewById(R.id.parent_layout);
        }
    }
}