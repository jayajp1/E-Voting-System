package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drs.Model.Candidate;
import com.example.drs.Model.Count;
import com.example.drs.Model.Result;
import com.example.drs.resources.CandidateResource;
import com.example.drs.resources.VoteResource;
import com.example.drs.utils.RetrofitInst;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VoteResultActivity extends Activity {

    RecyclerView recyclerView;
    TextView vcout;
    String[] candidate_name,party_name;
    String [] candidate_photo;//={R.drawable.candidate1,R.drawable.candidate2} ;
    String [] party_symbol;//={R.drawable.bjp,R.drawable.congress};
    Integer [] votecount;
    ResultAdpter resultAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vcout=findViewById(R.id.rcvotecount);
        setContentView(R.layout.activity_vote_result);

        GlobalUser globalUser =(GlobalUser) getApplicationContext();
        String userCity = globalUser.getCity();
        String Votedone = globalUser.getVoteDone();
        recyclerView=findViewById(R.id.voteresultrec);
        Log.d("opopop","Hello"+"hihihihi");
        Retrofit r2 = RetrofitInst.getRetrofit();
        VoteResource voteResource = r2.create(VoteResource.class);
        Call<List<Count>> c = voteResource.votecount();
        Log.d("opopop","Hello"+"hihihihi");
        c.enqueue(new Callback<List<Count>>() {
            @Override
            public void onResponse(Call<List<Count>> call, Response<List<Count>> response) {
                List<Count> aftercall = response.body();
                Log.d("opopop","Hello"+aftercall.isEmpty());
                if(!aftercall.isEmpty()) {
                    candidate_name = new String[aftercall.size()];
                    party_symbol = new String[aftercall.size()];
                    party_name = new String[aftercall.size()];
                    candidate_photo = new String[aftercall.size()];
                    votecount = new Integer[aftercall.size()];

                    for (int i = 0; i < aftercall.size(); i++) {
                        candidate_name[i] = (aftercall.get(i).getCandidate());
                        party_name[i] = aftercall.get(i).getPartyname();
                        Log.d("OOOOOO","helo"+aftercall.get(i).getParty_img());
                        party_symbol[i] = aftercall.get(i).getParty_img();
                        candidate_photo[i] = aftercall.get(i).getCandidate_img();
                        votecount[i] = aftercall.get(i).getCount();
                    }
                    Log.d("ooooooq",""+party_symbol[0]);
//                    Log.d("ooooooq",""+party_symbol[1]);
                    resultAdpter = new ResultAdpter(getApplicationContext(), candidate_name, party_name, party_symbol, candidate_photo, votecount);
                    recyclerView.setAdapter(resultAdpter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }
                }

            @Override
            public void onFailure(Call<List<Count>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection Error",Toast.LENGTH_SHORT).show();
            }
        });

      /*  Retrofit r = RetrofitInst.getRetrofit();
        CandidateResource candidateResource = r.create(CandidateResource.class);
        Call<List<Candidate>> c = candidateResource.selectbycity(userCity);
        c.enqueue(new Callback<List<Candidate>>() {
            @Override
            public void onResponse(Call<List<Candidate>> call, Response<List<Candidate>> response) {
                List<Candidate> aftercall = response.body();
                if (!aftercall.isEmpty()) {
                    candidate_name = new String[aftercall.size()];
                    party_symbol = new String[aftercall.size()];
                    party_name = new String[aftercall.size()];
                    candidate_photo = new String[aftercall.size()];
                    Log.d("size", "" + aftercall.size());
                    for (int i = 0; i < aftercall.size(); i++) {
                        Log.d("size", "" + aftercall.get(i).getFullName());
                        candidate_name[i] = (aftercall.get(i).getFullName());
                        party_name[i] = aftercall.get(i).getPartyname();
                        party_symbol[i] = aftercall.get(i).getPartyphotopath();
                        candidate_photo[i] = aftercall.get(i).getCandidatePhotopath();
                    }

                    resultAdpter = new ResultAdpter(getApplicationContext(),candidate_name ,party_name , party_symbol, candidate_photo);
                    recyclerView.setAdapter(resultAdpter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Candidate>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection Error",Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
