package com.braun1792.comp259finalproject;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //adds the app bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        initializeContacts();

        if (savedInstanceState == null){
            //launch ContactListFragment - entry point to the app
            getFragmentManager().beginTransaction().add(R.id.fragmentContainer, new ContactListFragment()).commit();
        }

    }

    public void initializeContacts(){

        DBHelper database = new DBHelper(this);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.action_add:
                // User chose the "Add Contact" action
                // launch add contact fragment
                fragmentTransaction.replace(R.id.fragmentContainer,new AddContactFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
            case R.id.action_quit:
                //User chose "Quit" action, close app
                finish();
            case R.id.action_delete:
                //User chose "Delete contact" from the ContactDetailFragment
                //delete contact
                deleteContact();
                //Launch contact list fragment
                ContactListFragment cf = new ContactListFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, cf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            case R.id.action_edit:
                //User chose "Edit Contact" from the ContactDetailFragment
                ContactDetailFragment cdf = (ContactDetailFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
                cdf.launchEditContact();
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
        acf.saveContact(view);
    }

    public void deleteContact(){
        ContactDetailFragment cdf = (ContactDetailFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        int contactRecord = cdf.getContactRecord();
        DBHelper database = new DBHelper(this);
        Contact toDelete = database.getContact(contactRecord);
        database.deleteContact(toDelete);
    }

    public void updateContact(View view){
        ContactUpdateFragment cuf = (ContactUpdateFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        cuf.updateContact();

    }
}
