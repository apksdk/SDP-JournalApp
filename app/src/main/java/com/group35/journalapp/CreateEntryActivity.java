package com.group35.journalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group35.journalapp.models.Entry;
import com.group35.journalapp.models.EntryContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateEntryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    private String mJournalID;

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

    @BindView(R.id.addMediaIV)
    ImageView addMediaIV;

    @BindView(R.id.createEntryLayout)
    ConstraintLayout createEntryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
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
        mJournalID = intent.getStringExtra("journalID");
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_journals) {
            // Handle the camera action
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.entryConfirmBTN)
    public void saveHandler(View view) {
        // Get all information
        String entryTitle = entryTitleET.getText().toString();
        String entryAuthor = mUser.getDisplayName();
        String entryNotes = entryNotesET.getText().toString();
        String entryObligations = entryObligationsET.getText().toString();
        String entryDecisions = entryDecisionsET.getText().toString();
        String entryOutcomes = entryOutcomesET.getText().toString();

        String date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssa", Locale.getDefault()).format(new Date());
        Entry entry = new Entry(entryTitle, entryNotes, date, 0);
        EntryContent entryContent = new EntryContent(entryNotes, entryObligations, entryDecisions, entryOutcomes, date, 0);
        DatabaseReference entryRef = mDatabase.getReference();
        // Save entry to database
        if (!entryTitle.isEmpty()) {
            ArrayList<String> entryContentsList = new ArrayList<>();
            entryContentsList.add(entryRef.push().getKey());

            entry.setEntryContentList(entryContentsList);
            String entryID = entryRef.push().getKey();
            entryRef.child("Entries").child(mUser.getDisplayName()).child(mJournalID).child(entryID).setValue(entry);
            entryRef.child("EntryContents").child(mUser.getDisplayName()).child(entryID).child(entryContentsList.get(0)).setValue(entryContent).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(CreateEntryActivity.this, "You have successfully created an entry!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            entryTitleET.setError("Missing Entry Title!");
        }
    }

    @OnClick(R.id.cancelEntryBTN)
    public void cancelEntryHandler(View view) {
        Boolean isEmpty = true;

        for (int i = 0; i < createEntryLayout.getChildCount(); i++) {
            if (createEntryLayout.getChildAt(i) instanceof EditText) {
                EditText currentET = (EditText) createEntryLayout.getChildAt(i);
                if (!currentET.getText().toString().isEmpty()) {
                    isEmpty = false;

                    new AlertDialog.Builder(CreateEntryActivity.this)
                            .setTitle("Exit Confirmation")
                            .setMessage("Are you sure you want to exit without saving your changes?")
                            .setIcon(R.drawable.warning)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        }

        if (isEmpty) {
            finish();
        }
    }
}
