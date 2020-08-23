package com.example.drs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drs.Model.Result;
import com.example.drs.resources.ResultResource;
import com.example.drs.utils.RetrofitInst;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminSideBeforeVote extends Activity {

    private Spinner spinner;
    private  Button button;
    private String Choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side_before_vote);

        spinner = findViewById(R.id.aChoice);
        button=findViewById(R.id.abtnchoice);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choice=spinner.getSelectedItem().toString();
                Result result=new Result();
                result.setChoice(Choice);
                result.setId(1);
                Retrofit r = RetrofitInst.getRetrofit();
                ResultResource resultResource = r.create(ResultResource.class);
                Call<Boolean> c =resultResource.Updateresult(result);

                c.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.body())
                        {
                            Toast.makeText(getApplicationContext(),"Choice is Successfully Change",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please try again ",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"please try again Failure Occured",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}
