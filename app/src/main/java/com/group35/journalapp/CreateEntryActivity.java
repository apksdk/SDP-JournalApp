package com.group35.journalapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group35.journalapp.models.EntryContent;
import com.group35.journalapp.models.Journal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateEntryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    @BindView(R.id.entryTitleET)
    EditText entryTitleET;

    @BindView(R.id.entryNotesET)
    EditText entryNotesET;

    @BindView(R.id.entryObligationsET)
    EditText entryObligationsET;

    @BindView(R.id.entryDecisionsET)
    EditText entryDecisionsET;

    @BindView(R.id.entryOutcomesET)
    EditText entryOutcomesET;

    @BindView(R.id.addMediaIBTN)
    ImageButton addMediaIBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_journals) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.confirmBTN)
    public void saveHandler(View view) {
        // Get all information
        String entryTitle = entryTitleET.getText().toString();
        String entryAuthor = mUser.getDisplayName();
        String entryNotes = entryNotesET.getText().toString();
        String entryObligations = entryObligationsET.getText().toString();
        String entryDecisions = entryDecisionsET.getText().toString();
        String entryOutcomes = entryOutcomesET.getText().toString();

        EntryContent entryContent = new EntryContent(entryTitle, entryNotes, entryObligations, entryDecisions, entryOutcomes);
        DatabaseReference entryRef = mDatabase.getReference();
        // Save entry to database
        if (!entryTitle.isEmpty()) {
            ArrayList<String> entryContentsList = new ArrayList<>();
            entryContentsList.add(entryRef.push().getKey().toString());
            String entryID = entryRef.push().getKey().toString();

            entryRef.child("Entry").child(mUser.getDisplayName()).push().child("EntryContents").setValue(entryContentsList.get(0));
            entryRef.child("EntryContents").child(mUser.getDisplayName()).child(entryID).setValue(entryContentsList.get(0));
        //Save
        } else {
            entryTitleET.setError("Missing Entry Title!");
        }
    }
}
