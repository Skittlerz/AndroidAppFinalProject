package com.braun1792.comp259finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
            //add 1 because database ids start at 1 not 0
            contactRecord = bundle.getInt("contactRecord",0) + 1;
            DBHelper database = new DBHelper(getActivity());
            Contact selectedContact = database.getContact(contactRecord);
            setDisplay(rootView,selectedContact);
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.details_title);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cd_menu, menu);
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

    public void launchEditContact(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ContactUpdateFragment cuf = new ContactUpdateFragment();
        //pass contactRecord as a bundle
        final Bundle bundle = new Bundle();
        bundle.putInt("contactRecord",contactRecord);
        cuf.setArguments(bundle);
        //launch Contact Update Fragment
        fragmentTransaction.replace(R.id.fragmentContainer, cuf);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
