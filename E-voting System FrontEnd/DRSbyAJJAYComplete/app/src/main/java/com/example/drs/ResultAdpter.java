package com.example.drs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drs.MainActivity;
import com.example.drs.R;
import com.example.drs.VoteAdpter;
import com.example.drs.resources.VoteResource;
import com.example.drs.utils.RetrofitInst;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResultAdpter extends RecyclerView.Adapter<ResultAdpter.ViewHolder> {

    Context context;
    String[] candidate_name,party_name;
    String[] party_symbol;
    String[] candidate_photo;
    Integer[] votercount;


    public ResultAdpter(Context context, String[] candidate_name, String[] party_name, String[] party_symbol, String[] candidate_photo,Integer[] votecount) {
        this.context = context;
        //Log.d("size",candidate_name[1]);
        this.candidate_name = candidate_name;
        this.party_name = party_name;
        this.party_symbol = party_symbol;
        this.candidate_photo = candidate_photo;
        this.votercount=votecount;

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    public ResultAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.activity_resultcarditems,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        {

            //Picasso.get().load(candidate_photo[position]).into(holder.cand_pic);
            Picasso.with(context).load(candidate_photo[position]).into(holder.cand_pic);
            holder.cand_name.setText(candidate_name[position]);

            Picasso.with(context).load(party_symbol[position]).into(holder.party_pic);
            //Picasso.get().load(party_symbol[position]).into(holder.party_pic);
            holder.party_name.setText(party_name[position]);
            holder.votecount.setText(""+votercount[position]);
        }
    }

    @Override
    public int getItemCount() {
        return candidate_name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cand_pic,party_pic;
        TextView cand_name,party_name,votecount;
        @RequiresApi(api = Build.VERSION_CODES.P)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cand_pic=itemView.findViewById(R.id.rcimgview1);
            party_pic=itemView.findViewById(R.id.rcimgview2);
            cand_name=itemView.findViewById(R.id.rcvtv1);
            party_name=itemView.findViewById(R.id.rcvtv2);
            votecount=itemView.findViewById(R.id.rcvotecount);


        }
    }
}