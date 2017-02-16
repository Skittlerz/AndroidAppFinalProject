package com.braun1792.comp259finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by braun1792 on 2/15/2017.
 */
public class ContactDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.details_title);
        return inflater.inflate(R.layout.contact_detail_fragment,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set app bar title when fragment is resumed
        // ex. User hits back button
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.details_title);
    }
}
