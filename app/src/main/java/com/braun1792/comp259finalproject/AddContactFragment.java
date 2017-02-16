package com.braun1792.comp259finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by braun1792 on 2/15/2017.
 */
public class AddContactFragment extends Fragment {

    EditText name;
    EditText phone;
    EditText email;
    EditText address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //set app bar title
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.add_title);
        View rootView = inflater.inflate(R.layout.add_contact_fragment,container,false);
        //set up edit text
        name = (EditText) rootView.findViewById(R.id.etName);
        phone = (EditText) rootView.findViewById(R.id.etPhone);
        email = (EditText) rootView.findViewById(R.id.etEmail);
        address = (EditText) rootView.findViewById(R.id.etAddress);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set app bar title when fragment is resumed
        // ex. User hits back button
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.add_title);
    }

    public void saveContact(View view){
        //get contact info from edit texts
        Contact contact = new Contact();
        contact.setName(name.getText().toString());
        contact.setPhone(phone.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setAddress(address.getText().toString());
        //use DBHelper to add new contact to the database
        DBHelper database = new DBHelper(getActivity());
        database.addContact(contact);
        //after the new contact is added return to ContactListFragment view
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ContactListFragment cf = new ContactListFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, cf);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
