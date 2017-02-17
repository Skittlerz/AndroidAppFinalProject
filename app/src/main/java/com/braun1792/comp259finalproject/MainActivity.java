package com.braun1792.comp259finalproject;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contactData;
    ArrayList<String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // adds the app bar
        // my_toolbar exists in the activity_main.xml
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Contacts are already stored in the database do not need to call this anymore
        //initializeContacts();

        updateContactList();

        if (savedInstanceState == null){
            //launch ContactListFragment - entry point to the app
            ContactListFragment clf = new ContactListFragment();
            final Bundle bundle = new Bundle();
            bundle.putStringArrayList("contactList",contacts);
            clf.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.fragmentContainer,clf).commit();
        }

    }

    public void initializeContacts(){

        DBHelper database = new DBHelper(this);
        //add records to the database
        database.addContact(new Contact(1,"Amanda B","306-458-9722","a@email.com","123 3rd ave"));
        database.addContact(new Contact(2,"Buff B","306-547-3588","b@email.com","123 3rd ave"));
        database.addContact(new Contact(3,"Chris B","204-900-4056","c@email.com","1 River"));
        database.addContact(new Contact(4,"Michelle P","204-780-9543","m@email.com","300 Munroe"));
        database.addContact(new Contact(5,"Joe H","204-667-9807","j@email.com","100 Osborne"));
        database.addContact(new Contact(6,"Tobias F","507-990-8654","t@email.com","2109 1st ave w"));
        database.addContact(new Contact(7,"Maybe M","607-876-3421","mm@email.com","3098 2nd ave e"));
        database.addContact(new Contact(8,"Maria J","204-990-9034","mj@email.com","790 Winterton"));
        database.addContact(new Contact(9,"Steph S","204-654-0987","s@email.com","PO BOX 3490"));
        database.addContact(new Contact(10,"James S","204-780-2345","js@email.com","23 Sutton"));

    }

    //ensures that the MainActivity contact ArrayLists are current
    public void updateContactList(){
        //collect data for contacts from database
        DBHelper database = new DBHelper(this);
        contactData = database.getAllContacts();

        //get list of just names from contactData
        //this is what will appear in the UI list of contacts
        contacts = new ArrayList<>();
        for(int i = 0; i < contactData.size();i++){
            contacts.add(contactData.get(i).getName());
        }
    }

    public int getContactId(int pos){

        return contactData.get(pos).getId();

    }

    public Contact getContactById(int id){

        Contact c = new Contact();
        for(int i=0;i<contactData.size();i++){

            if(contactData.get(i).getId() == id){
                c = contactData.get(i);
            }
        }

        return c;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                // User chose the "Add Contact" action
                // launch add contact fragment
                launchAddContact();
                return true;
            case R.id.action_quit:
                //User chose "Quit" action, close app
                finish();
                return true;
            case R.id.action_delete:
                //User chose "Delete contact" from the ContactDetailFragment or ContactUpdateFragment
                //delete contact
                deleteContact();
                updateContactList();
                //Launch contact list fragment
                launchContactList();
                return true;
            case R.id.action_edit:
                //User chose "Edit Contact" from the ContactDetailFragment
                launchEditContact();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    public void saveContact(View view){
        //access the AddContactFragment's save method to save the contact
        AddContactFragment acf = (AddContactFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        Contact c = acf.getNewContact();
        //Name is required field
        //if null throw error message via toast
        if(!c.getName().equals("")) {
            DBHelper database = new DBHelper(this);
            database.addContact(c);
            updateContactList();
            launchContactList();
        }else{
            Toast.makeText(this,"Error: Name Is Required",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteContact(){
        //grabs contact record position and deletes
        //delete request can come from either the ContactDetailFragment or ContactUpdateFragment
        int contactRecord;
        Fragment f = getFragmentManager().findFragmentById(R.id.fragmentContainer);
        if(f instanceof ContactDetailFragment) {
            contactRecord = ((ContactDetailFragment)f).getContactRecord();
        }else{
            contactRecord = ((ContactUpdateFragment)f).getContactRecord();
        }
        DBHelper database = new DBHelper(this);
        Contact toDelete = getContactById(contactRecord);
        database.deleteContact(toDelete);
    }

    public void updateContact(View view){

        //access the ContactUpdateFragment's update method to update contact
        ContactUpdateFragment cuf = (ContactUpdateFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        Contact c = cuf.getEditedContact();
        // use DBHelper to update contact to the database
        DBHelper database = new DBHelper(this);
        database.editContact(c);
        //update arrayList after edit
        updateContactList();
        // after the contact is updated return to ContactListFragment view
        launchContactList();
    }

    public void launchEditContact(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ContactUpdateFragment cuf = new ContactUpdateFragment();
        //pass contactRecord as a bundle
        final Bundle bundle = new Bundle();
        bundle.putInt("contactRecord",((ContactDetailFragment)getFragmentManager().
                findFragmentById(R.id.fragmentContainer)).getContactRecord());
        cuf.setArguments(bundle);
        //launch Contact Update Fragment
        fragmentTransaction.replace(R.id.fragmentContainer, cuf);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void launchContactList(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ContactListFragment cf = new ContactListFragment();
        //pass arraylist of contact names as bundle
        final Bundle bundle = new Bundle();
        bundle.putStringArrayList("contactList",contacts);
        cf.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer, cf);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void launchAddContact(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,new AddContactFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
