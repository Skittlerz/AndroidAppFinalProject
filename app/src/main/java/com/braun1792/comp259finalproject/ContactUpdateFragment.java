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
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by braun1792 on 2/16/2017.
 */
public class ContactUpdateFragment extends Fragment {
    int contactRecord;
    EditText name;
    EditText phone;
    EditText email;
    EditText address;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.update_contact_fragment,container,false);
        // allows for the app bar to be changed
        setHasOptionsMenu(true);
        // contact record number should be passed from ContactDetailFragment
        Bundle bundle = this.getArguments();
        if(bundle != null){
            contactRecord = bundle.getInt("contactRecord",0);
            //use contact record to grab all contact info and display for editing
            Contact selectedContact = ((MainActivity)getActivity()).getContactById(contactRecord);
            setEditDisplay(rootView,selectedContact);
        }
        // change title of app bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.edit_contact);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set app bar title when fragment is resumed
        // ex. User hits back button
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.edit_contact);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // remove the add new contact icon and update icon
        // do now want this showing from the update contact screen
        inflater.inflate(R.menu.cd_menu, menu);
        menu.findItem(R.id.action_add).setVisible(false);
        menu.findItem(R.id.action_edit).setVisible(false);
    }

    public void setEditDisplay(View view, Contact contact){
        // display contact details
        name = (EditText) view.findViewById(R.id.etNameEdit);
        name.setText(contact.getName());
        phone = (EditText) view.findViewById(R.id.etPhoneEdit);
        phone.setText(contact.getPhone());
        email = (EditText) view.findViewById(R.id.etEmailEdit);
        email.setText(contact.getEmail());
        address = (EditText) view.findViewById(R.id.etAddressEdit);
        address.setText(contact.getAddress());

    }

    public int getContactRecord(){
        // returns contact record number currently in use by this fragment
        return contactRecord;
    }

    public Contact getEditedContact(){
        // this method is called by the MainActivity
        // triggered by user pressing save from the ContactUpdateFragment
        // get contact info from edit texts
        Contact contact = new Contact();
        contact.setId(contactRecord);
        contact.setName(name.getText().toString());
        contact.setPhone(phone.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setAddress(address.getText().toString());

        return contact;
    }
}
