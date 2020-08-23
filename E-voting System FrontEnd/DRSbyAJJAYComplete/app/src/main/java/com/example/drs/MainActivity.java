package com.example.drs;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.drs.Model.Result;
import com.example.drs.resources.ResultResource;
import com.example.drs.resources.VoterResource;
import com.example.drs.utils.RetrofitInst;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private static final int PERMISSION_REQUEST_CODE = 300;
    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final GlobalUser globalUser = (GlobalUser) getApplicationContext();
        Retrofit r = RetrofitInst.getRetrofit();
        ResultResource resultResource = r.create(ResultResource.class);
        Call<List<Result>> c =resultResource.getAll();
        c.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                String Choice= response.body().get(0).getChoice();
                globalUser.setChoice(Choice);
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                Log.d("oooppp","fail");
            }
        });


        if(getIntent().getStringExtra("vdone") !=null) {

            globalUser.setVoteDone("true");
            Retrofit r1 = RetrofitInst.getRetrofit();
            VoterResource voterResource = r1.create(VoterResource.class);
            Call<Boolean> c1 = voterResource.updatevdone(globalUser.getUsername(),"true");

            c1.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT).show();
                }
            });

        }
        if(getIntent().getStringExtra("FromScanning") != null) {
            Log.d("Main", "onCreate: ");
            loadFragment(new ScannerFragment());
        } else {
            loadFragment(new HomeFragment());
        }

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;

            case R.id.nav_news:
                fragment = new NewsFragment();
                break;

            case R.id.nav_vote:
                fragment = new VoteFragment();
                break;

            case R.id.nav_qrscan:
                fragment = new ScannerFragment();
                break;

            case R.id.nav_User:
                fragment = new UsersettingFragment();
                break;
        }
        return loadFragment(fragment);
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.drs_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}