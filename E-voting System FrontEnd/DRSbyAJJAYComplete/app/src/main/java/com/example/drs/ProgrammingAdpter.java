package com.example.drs;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProgrammingAdpter extends RecyclerView.Adapter<ProgrammingAdpter.ProgramminViewHolder> {

      String[] data;
        public ProgrammingAdpter(String[] data)
        {
            this.data=data;
        }
    @NonNull
    @Override
    public ProgramminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramminViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class  ProgramminViewHolder extends RecyclerView.ViewHolder{
        public ProgramminViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
