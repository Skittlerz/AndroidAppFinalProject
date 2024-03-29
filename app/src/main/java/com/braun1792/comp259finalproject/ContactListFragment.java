package com.braun1792.comp259finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by braun1792 on 2/15/2017.
 */
public class ContactListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ArrayList<String> contacts = new ArrayList<>();

        Bundle bundle = this.getArguments();
        if(bundle != null){
            //get arraylist of contact names passed from main activity
            contacts = bundle.getStringArrayList("contactList");
        }

        ArrayAdapter<String> contactListAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.contact_list_item, R.id.contact_list_item_textView,contacts);

        View rootView = inflater.inflate(R.layout.contact_list_fragment, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.lvContacts);
        listView.setAdapter(contactListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ContactDetailFragment cdf = new ContactDetailFragment();
                //pass input values as a bundle
                final Bundle bundle = new Bundle();
                bundle.putInt("contactRecord",((MainActivity)getActivity()).getContactId(position));
                cdf.setArguments(bundle);
                //launch Contact Detail Fragment
                fragmentTransaction.replace(R.id.fragmentContainer, cdf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title when fragment is resumed
        // ex. User hits back button
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
    }
}
