package com.example.drs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UsersettingFragment extends Fragment {
    Button bthelp,btfeedback,btgenerateqr,btlogout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_usersetting, null);
        btfeedback = (Button) root.findViewById(R.id.btnfeedback);

        btlogout=(Button)root.findViewById(R.id.btnuserlogout);
        bthelp=(Button)root.findViewById(R.id.btnhelp);
        btgenerateqr=(Button)root.findViewById(R.id.btnusgenerateqr);

        btgenerateqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getActivity(),UserqrActivity.class);
                startActivity(ii);
            }
        });
        bthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getActivity(),FAQs.class);
                startActivity(ii);
            }
        });

       btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getActivity(),loginpage.class);
                startActivity(ii);
            }
      });

        btfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getActivity(), Feedback.class);
                startActivity(ii);
            }
        });

        return root;
    }
}

