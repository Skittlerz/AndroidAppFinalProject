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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.add_contact_fragment,container,false);
        //set up edit text
        name = (EditText) rootView.findViewById(R.id.etName);
        phone = (EditText) rootView.findViewById(R.id.etPhone);
        email = (EditText) rootView.findViewById(R.id.etEmail);
        address = (EditText) rootView.findViewById(R.id.etAddress);

        return rootView;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // remove the add new contact icon
        // already in the add new contact screen
        menu.findItem(R.id.action_add).setVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set app bar title when fragment is resumed
        // ex. User hits back button
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.add_title);
    }

    public Contact getNewContact(){

            //get contact info from edit texts
            Contact contact = new Contact();
            contact.setName(name.getText().toString());
            contact.setPhone(phone.getText().toString());
            contact.setEmail(email.getText().toString());
            contact.setAddress(address.getText().toString());
            return contact;
    }
}
