package com.braun1792.comp259finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
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

        //collect data for contacts
        String [] contactData = {"Amanda","Buff","Chris","Michelle"};
        List<String> contacts = new ArrayList<>(Arrays.asList(contactData));

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
                bundle.putInt("Position",position);
                cdf.setArguments(bundle);
                //launch Contact Detail Fragment
                fragmentTransaction.replace(R.id.fragmentContainer, cdf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return rootView;

    }
}
