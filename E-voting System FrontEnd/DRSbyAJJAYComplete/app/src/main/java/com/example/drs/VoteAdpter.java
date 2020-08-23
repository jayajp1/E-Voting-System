package com.example.drs;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drs.resources.VoteResource;
import com.example.drs.utils.RetrofitInst;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VoteAdpter extends RecyclerView.Adapter<VoteAdpter.ViewHolder> {

    Context context;
    String[] candidate_name,party_name;
    String[] party_symbol;
    String[] candidate_photo;


    public VoteAdpter(Context context, String[] candidate_name, String[] party_name, String[] party_symbol, String[] candidate_photo) {
        this.context = context;
        //Log.d("size",candidate_name[1]);
        this.candidate_name = candidate_name;
        this.party_name = party_name;
        this.party_symbol = party_symbol;
        this.candidate_photo = candidate_photo;

    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.activity_carditems,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        //Picasso.get().load(candidate_photo[position]).into(holder.cand_pic);
     Picasso.with(context).load(candidate_photo[position]).into(holder.cand_pic);
        holder.cand_name.setText(candidate_name[position]);

        Picasso.with(context).load(party_symbol[position]).into(holder.party_pic);
      //Picasso.get().load(party_symbol[position]).into(holder.party_pic);
        holder.party_name.setText(party_name[position]);
        final VoteAdpter voteAdpter=this;


        holder.vote.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {

                final Executor executor= Executors.newSingleThreadExecutor();
                final BiometricPrompt biometricPrompt;
                     biometricPrompt = new BiometricPrompt.Builder(context.getApplicationContext())
                            .setTitle("Fingerprint Authentication")
                            .setDescription("This is require for Security..")
                            .setNegativeButton("Cancel",executor, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).build();


                biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        // super.onAuthenticationSucceeded(result);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context.getApplicationContext(),"Authentication Completed.",Toast.LENGTH_LONG).show();
                            }


                        });

                        Retrofit r = RetrofitInst.getRetrofit();
                        VoteResource voteResource = r.create(VoteResource.class);

                        Call<Boolean> c=voteResource.persist(candidate_name[position],party_name[position]);

                        c.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.body())
                                {
                                    Toast.makeText(context,"Vote Done..",Toast.LENGTH_SHORT).show();
                                    Intent ii=new Intent(context,MainActivity.class);
                                    ii.putExtra("vdone","true");
                                    context.startActivity(ii);


                                }
                                else
                                {
                                    Toast.makeText(context,"Something Went Wrong with data",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                                Toast.makeText(context,"Connection Error.",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }
});

    }

    @Override
    public int getItemCount() {
        return candidate_name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cand_pic,party_pic;
        TextView cand_name,party_name;
        Button vote;
        @RequiresApi(api = Build.VERSION_CODES.P)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cand_pic=itemView.findViewById(R.id.vimgview1);
            party_pic=itemView.findViewById(R.id.vimgview2);
            cand_name=itemView.findViewById(R.id.vtv2);
            party_name=itemView.findViewById(R.id.vtv1);
            vote=itemView.findViewById(R.id.btnvotte);


        }
    }
}