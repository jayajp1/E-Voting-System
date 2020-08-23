package com.example.drs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.drs.Model.Articles;
import com.example.drs.Model.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText etQuery;
    Button btnSearch,btnAboutUs;
    Dialog dialog;
    final String API_KEY = "57442c2383d04c6ca1bd1362bf36d333";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, null);

        swipeRefreshLayout = root.findViewById(R.id.swipeRefresh);
        recyclerView = root.findViewById(R.id.recyclerView);
        etQuery = root.findViewById(R.id.etQuery);
        btnSearch = root.findViewById(R.id.btnSearch);
        btnAboutUs = root.findViewById(R.id.aboutUs);
       // dialog = new Dialog(NewsFragment.this);
        dialog = new Dialog(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));;
      //  recyclerView.setLayoutManager(new LinearLayoutManager(this));;
        //  final String country = getCountry();
        final String country = "in";

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("",country,API_KEY);
            }
        });
        retrieveJson("",country,API_KEY);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etQuery.getText().toString().equals("")){
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson(etQuery.getText().toString(),country,API_KEY);
                        }
                    });
                    retrieveJson(etQuery.getText().toString(),country,API_KEY);
                }else{
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson("",country,API_KEY);
                        }
                    });
                    retrieveJson("",country,API_KEY);
                }
            }
        });
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
     //   Intent ii = new Intent(getActivity(), NewsActivity.class);
       // startActivity(ii);
        return root;
    }
    public void retrieveJson(String query ,String country, String apiKey){

        swipeRefreshLayout.setRefreshing(true);
        Call<Headlines> call;
        if (!etQuery.getText().toString().equals("")){
            call= ApiClient.getInstance().getApi().getSpecificData(query,apiKey);
        }else{
            call= ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        }

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    //adapter = new Adapter(NewsFragment.this,articles);
                    adapter = new Adapter(getActivity(),articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
              //  Toast.makeText(NewsFragment.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        // String country = locale.getCountry();
        String country="in";
        return country.toLowerCase();
    }

    public void showDialog(){
        Button btnClose;
        dialog.setContentView(R.layout.about_us_pop_up);
        dialog.show();
        btnClose = dialog.findViewById(R.id.close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}

