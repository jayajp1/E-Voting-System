package com.example.drs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

   TextView tvvotername,tvvotercity,tvvotermobile;
   ViewFlipper viewFlipper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, null);
        GlobalUser globalUser = (GlobalUser) getActivity().getApplicationContext();
        String firstname = globalUser.getFirstname();
        String city=globalUser.getCity();
        String mobile=globalUser.getMobileNo();
        tvvotername=root.findViewById(R.id.vname);
        tvvotercity=root.findViewById(R.id.vciity);
        tvvotermobile=root.findViewById(R.id.vcontact);
        tvvotername.setText("Welcome "+firstname);
        tvvotercity.setText("City : "+city);
        tvvotermobile.setText("Mobile.No :"+mobile);

             viewFlipper=root.findViewById(R.id.viewflipeer);
        int imgs[]={R.drawable.sllider5,R.drawable.slider2,R.drawable.slider3,R.drawable.slider1};
        for (int imgss:imgs)
        {
            flipImages(imgss);
        }
        return root;
    }
    public void flipImages(int imgs)
    {
        ImageView imageView=new ImageView(getActivity().getApplicationContext());
        imageView.setBackgroundResource(imgs);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setOutAnimation(getActivity().getApplicationContext(),android.R.anim.slide_out_right);
        viewFlipper.setInAnimation(getActivity().getApplicationContext(),android.R.anim.slide_in_left);

    }
}


