package com.group35.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group35.journalapp.models.EntryContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditSingleEntryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    private String mEntryID;
    private String mEntryObligations;
    private String mEntryDecisions;
    private String mEntryOutcome;
    private String mEntryNotes;
    private String mEntryVersion;
    private String mJournalID;

    @BindView(R.id.obligationsContentET)
    EditText obligationsContentET;

    @BindView(R.id.outcomeContentET)
    EditText outcomeContentET;

    @BindView(R.id.notesContentET)
    EditText notesContentET;

    @BindView(R.id.decisionsContentET)
    EditText decisionsContentET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        mEntryID = intent.getStringExtra("entryID");
        mEntryObligations = intent.getStringExtra("entryObligations");
        mEntryDecisions = intent.getStringExtra("entryDecisions");
        mEntryOutcome = intent.getStringExtra("entryOutcome");
        mEntryNotes = intent.getStringExtra("entryNotes");
        mEntryVersion = intent.getStringExtra("entryVersion");
        mJournalID = intent.getStringExtra("journalID");

        obligationsContentET.setText(mEntryObligations);
        decisionsContentET.setText(mEntryDecisions);
        outcomeContentET.setText(mEntryOutcome);
        notesContentET.setText(mEntryNotes);
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
        getMenuInflater().inflate(R.menu.edit_single_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            final String newObligations = obligationsContentET.getText().toString();
            final String newDecisions = decisionsContentET.getText().toString();
            final String newOutcome = outcomeContentET.getText().toString();
            final String newNotes = notesContentET.getText().toString();

            if (newObligations.equals(mEntryObligations) && newDecisions.equals(mEntryDecisions)
                    && newOutcome.equals(mEntryOutcome) && newNotes.equals(mEntryNotes)) {
                Toast.makeText(EditSingleEntryActivity.this, "You have not made any changes.", Toast.LENGTH_SHORT).show();
            } else {
                final ArrayList<String> entryContentList = new ArrayList<>();

                //Save to database
                final DatabaseReference entryRef = mDatabase.getReference().child("Entries").child(mUser.getDisplayName())
                        .child(mJournalID).child(mEntryID).child("entryContentList");

                final String newEntry = entryRef.push().getKey();

                entryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            entryContentList.add(snapshot.getValue(String.class));
                        }
                        String date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssa", Locale.getDefault()).format(new Date());
                        EntryContent entryContent = new EntryContent(newNotes, newObligations, newDecisions, newOutcome, date, entryContentList.size());
                        entryContentList.add(newEntry);
                        entryRef.setValue(entryContentList);

                        DatabaseReference journalDateRef = mDatabase.getReference().child("users").child(mUser.getDisplayName())
                                .child("Journals").child(mJournalID).child("journalModifiedDate");
                        journalDateRef.setValue(date);

                        DatabaseReference entryDescriptionRef = mDatabase.getReference().child("Entries").child(mUser.getDisplayName())
                                .child(mJournalID).child(mEntryID).child("entryPreview");
                        entryDescriptionRef.setValue(newNotes);

                        DatabaseReference entryContentRef = mDatabase.getReference().child("EntryContents").child(mUser.getDisplayName())
                                .child(mEntryID).child(newEntry);
                        entryContentRef.setValue(entryContent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
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
}