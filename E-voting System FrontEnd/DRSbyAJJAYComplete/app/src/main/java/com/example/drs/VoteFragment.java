package com.example.drs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drs.Model.Candidate;
import com.example.drs.Model.Result;
import com.example.drs.resources.CandidateResource;
import com.example.drs.resources.ResultResource;
import com.example.drs.utils.RetrofitInst;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VoteFragment extends Fragment {

    RecyclerView recyclerView;
    String[] candidate_name,party_name;
    String [] candidate_photo;//={R.drawable.candidate1,R.drawable.candidate2} ;
    String [] party_symbol;//={R.drawable.bjp,R.drawable.congress};
    VoteAdpter voteAdpter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        GlobalUser globalUser =(GlobalUser) getActivity().getApplicationContext();
        String userCity = globalUser.getCity();
        final String Votedone = globalUser.getVoteDone();
        String Choice=globalUser.getChoice();
        View root=inflater.inflate(R.layout.beforevotepage, null);

                 if(Choice.equals("BeforeVote"))
                 {
            View root1 = inflater.inflate(R.layout.beforevotepage, null); //set the layout of before vote
            return  root1;
                 }

                    if(Choice.equals("AfterVote") && Votedone.equals("true"))
                     {
            View root1 = inflater.inflate(R.layout.activity_votedone, null); //set the layout of aftervote
            return  root1;
                }
        if(Choice.equals("Vote") && Votedone.equals("true"))
        {
            Log.d("opopop",Choice+Votedone);
            View root1 = inflater.inflate(R.layout.activity_votedone, null); //set the layout of aftervote
            return  root1;
        }
        if (Choice.equals("Result"))
        {
            View root1 = inflater.inflate(R.layout.activity_vote_result, null);
            Intent ii=new Intent(getActivity(),VoteResultActivity.class);
            startActivity(ii);//set the layout of Result
            return  root1;

        }
                     if(Choice.equals("Vote") && Votedone.equals("false"))
                     {

            View root1 = inflater.inflate(R.layout.fragment_vote, null);

                recyclerView = root1.findViewById(R.id.rec1);
                //candidate_name=getResources().getStringArray(R.array.candidate_name);
                // candidate_photo=getResources().getIntArray(R.array.candidate_photo);
                // party_name=getResources().getStringArray(R.array.party_name);
                Retrofit r1 = RetrofitInst.getRetrofit();
                CandidateResource candidateResource = r1.create(CandidateResource.class);
                Call<List<Candidate>> c1 = candidateResource.selectbycity(userCity);

                c1.enqueue(new Callback<List<Candidate>>() {
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

                            voteAdpter = new VoteAdpter(getActivity(), candidate_name, party_name, party_symbol, candidate_photo);

                            recyclerView.setAdapter(voteAdpter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            Toast.makeText(getContext(), "Candiadte Load Successfully", Toast.LENGTH_SHORT).show();
                           // Intent ii=new Intent(getActivity().getApplicationContext(),Votedone.getClass());
                            //startActivity(ii);
                        } else {
                            Toast.makeText(getContext(), "No Candidate Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Candidate>> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });
            return root1;
        }
                     return  root;
            }
    }