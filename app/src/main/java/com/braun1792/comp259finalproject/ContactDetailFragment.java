package com.braun1792.comp259finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by braun1792 on 2/15/2017.
 */
public class ContactDetailFragment extends Fragment {
    TextView name;
    TextView phone;
    TextView email;
    TextView address;
    int contactRecord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.contact_detail_fragment,container,false);
        //allows for the app bar to be changed
        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            //take passed contact record and grab contact
            contactRecord = bundle.getInt("contactRecord",0);
            Contact selectedContact = ((MainActivity)getActivity()).getContactById(contactRecord);
            setDisplay(rootView,selectedContact);
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.details_title);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cd_menu, menu);
        menu.findItem(R.id.action_add).setVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set app bar title when fragment is resumed
        // ex. User hits back button
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.details_title);
    }

    public void setDisplay(View view, Contact contact){

        name = (TextView)view.findViewById(R.id.tvNameDetail);
        name.setText(contact.getName());
        phone = (TextView) view.findViewById(R.id.tvPhoneDetail);
        phone.setText(contact.getPhone());
        email = (TextView) view.findViewById(R.id.tvEmailDetail);
        email.setText(contact.getEmail());
        address = (TextView) view.findViewById(R.id.tvAddressDetail);
        address.setText(contact.getAddress());
    }

    public int getContactRecord(){
        return contactRecord;
    }

}
