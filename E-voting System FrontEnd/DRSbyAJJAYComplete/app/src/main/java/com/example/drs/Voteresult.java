package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drs.Model.Count;
import com.example.drs.resources.VoteResource;
import com.example.drs.utils.RetrofitInst;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Voteresult extends Activity {

    TextView Countvote1,c_name1,Party_Name1,Countvote2,c_name2,Party_Name2;
    ImageView party_img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voteresult);

        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
        Countvote1=findViewById(R.id.votecounnt1);
        c_name1=findViewById(R.id.win_Candidate_name1);
        Party_Name1=findViewById(R.id.win_party_name1);

        Countvote2=findViewById(R.id.votecounnt2);
        c_name2=findViewById(R.id.win_Candidate_name2);
        Party_Name2=findViewById(R.id.win_party_name2);

        //party_img1=findViewById(R.id.win_party_img1);
        String [] Pname={"BJP","INC"};

        Retrofit r = RetrofitInst.getRetrofit();
        VoteResource voteResource = r.create(VoteResource.class);
        Call<List<Count>> c = voteResource.votecount();

        c.enqueue(new Callback<List<Count>>() {
            @Override
            public void onResponse(Call<List<Count>> call, Response<List<Count>> response) {
                if(response.body()!=null)
                {
                    Count count=new Count();
                    count=response.body().get(0);
                    int Count_Vote=count.getCount();
                    String PartyName=count.getPartyname();
                    String Candidate=count.getCandidate();

                    c_name1.setText(Candidate);
                    Party_Name1.setText(PartyName);
                    Countvote1.setText(""+Count_Vote);

                    Count count2=new Count();
                    count2=response.body().get(1);
                    int Count_Vote2=count2.getCount();
                    String PartyName2=count2.getPartyname();
                    String Candidate2=count2.getCandidate();

                    c_name2.setText(Candidate2);
                    Party_Name2.setText(PartyName2);
                    Countvote2.setText(""+Count_Vote2);
                }
                else {
                    Toast.makeText(getApplicationContext(),"NO Result found ",Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<List<Count>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection is not establish..",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
