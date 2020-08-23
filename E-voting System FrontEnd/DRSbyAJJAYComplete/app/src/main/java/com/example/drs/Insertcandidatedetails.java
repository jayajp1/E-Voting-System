package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.drs.Model.Candidate;
import com.example.drs.resources.CandidateResource;
import com.example.drs.utils.RetrofitInst;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Insertcandidatedetails extends AppCompatActivity {

    Button ad_cd_btn_addcan,ad_cd_btnreset;
    EditText a_cd_fname, a_cd_adharcard, a_cd_partypic,a_cd_profle;
    Spinner a_cd_city,a_cd_partyname;
    String[] city,partyname;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertcandidatedetails);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        city=getResources().getStringArray(R.array.city_name);
        partyname=getResources().getStringArray(R.array.party_name);
        a_cd_city=findViewById(R.id.a_cd_city);
        ad_cd_btn_addcan=findViewById(R.id.ad_cd_btn_addcan);
        ad_cd_btnreset=findViewById(R.id.ad_cd_btnreset);
        a_cd_partypic=findViewById(R.id.a_cd_partypic);
        a_cd_profle=findViewById(R.id.a_cd_profle);

        a_cd_fname=findViewById(R.id.a_cd_fname);
        a_cd_partyname=findViewById(R.id.a_cd_partyname);
        a_cd_adharcard=findViewById(R.id.a_cd_adharcard);


        ArrayAdapter arrayAdapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,city);
        ArrayAdapter arrayAdapter2=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,partyname);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a_cd_city.setAdapter(arrayAdapter1);
        a_cd_partyname.setAdapter(arrayAdapter2);
        a_cd_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(city[position].getBytes().toString()!="Select") {
                    Toast.makeText(Insertcandidatedetails.this,city[position],Toast.LENGTH_LONG).show();  }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        a_cd_partyname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(partyname[position].getBytes().toString()!="Select") {
                    Toast.makeText(Insertcandidatedetails.this, partyname[position], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        validate();

        ad_cd_btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a_cd_fname.setText("");
                a_cd_adharcard.setText("");
            }
        });
        ad_cd_btn_addcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Candidate candidate=new Candidate();
                    candidate.setAdhar(Integer.parseInt(a_cd_adharcard.getText().toString()));
                    candidate.setFullName(a_cd_fname.getText().toString());
                    candidate.setCity(a_cd_city.getSelectedItem().toString());
                     candidate.setCity(a_cd_partyname.getSelectedItem().toString());

                    candidate.setPartyphotopath(a_cd_partypic.getText().toString());
                    candidate.setCandidatePhotopath(a_cd_profle.getText().toString());
                    Retrofit r = RetrofitInst.getRetrofit();
                    CandidateResource candidateResource = r.create(CandidateResource.class);
                    Call<Boolean> c = candidateResource.persist(candidate);

                    c.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            Boolean aftercall=response.body();
                            String af=aftercall.toString();
                            Log.d("registration",af);
                            if(aftercall) {
                                Toast.makeText(getApplicationContext(), "Candiadte Add Successfully..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), AdminTaskActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Please fill registration form", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Connection Error.", Toast.LENGTH_LONG).show();}

                    });
                }

        });


    }
    private void validate(){
        a_cd_fname=findViewById(R.id.a_cd_fname);
      //  a_cd_partyname=findViewById(R.id.a_cd_partyname);
        a_cd_adharcard=findViewById(R.id.a_cd_adharcard);

        awesomeValidation.addValidation(Insertcandidatedetails.this, R.id.a_cd_fname, "[a-zA-Z][a-zA-Z ]*", R.string.uname);
       // awesomeValidation.addValidation(Insertcandidatedetails.this, R.id. a_cd_partyname, "[a-zA-Z][a-zA-Z ]*", R.string.uname);
        awesomeValidation.addValidation(Insertcandidatedetails.this, R.id.a_cd_adharcard,"[0-9]{6,10}", R.string.uaad);
    }
}