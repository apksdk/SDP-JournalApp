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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

/**
 * The Create Entry Activity
 * <p>
 * Created by Joshua on 9/18/2017.
 */
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

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        ButterKnife.bind(this);
        //Initial UI setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Nav Menu Setup
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Set up request options for Glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(getResources().getDrawable(android.R.drawable.sym_def_app_icon));
        //Set user details on navigation menu
        final View navView = navigationView.getHeaderView(0);
        final TextView usernameNavTV = navView.findViewById(R.id.navUsernameTV);
        TextView emailNavTV = navView.findViewById(R.id.navEmailTV);
        ImageView userNavAvatarIV = navView.findViewById(R.id.navAvatarIV);
        usernameNavTV.setText(mUser.getDisplayName());

        //Set up nav image
        emailNavTV.setText(mUser.getEmail());
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(mUser.getPhotoUrl())
                .into(userNavAvatarIV);

        //Get intent
        Intent intent = getIntent();
        mJournalID = intent.getStringExtra("journalID");
    }

    /**
     * Closes the navigation drawer if it's open, otherwise exit the activity
     */
    @Override
    //TO DO: Check if the user has inputted anything, if there is confirm exit before quitting
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //TO DO: Confirm Exit
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //TO DO: Set up the navigation menu
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_journals) {
            startActivity(new Intent(getBaseContext(), ViewJournalsActivity.class));
        } else if (id == R.id.nav_view_logout) {
            mAuth.signOut();
            Toast.makeText(getBaseContext(), "You have signed out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Save entry handler. Saves the entry to the database.
     *
     * @param view the view
     */
    @OnClick(R.id.entryConfirmBTN)
    //TO DO: Check if ALL fields have data before saving
    public void saveHandler(View view) {
        // Get all information
        String entryTitle = entryTitleET.getText().toString();
        String entryAuthor = mUser.getDisplayName();
        String entryNotes = entryNotesET.getText().toString();
        String entryObligations = entryObligationsET.getText().toString();
        String entryDecisions = entryDecisionsET.getText().toString();
        String entryOutcomes = entryOutcomesET.getText().toString();
        //Get current time
        String date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssa", Locale.getDefault()).format(new Date());
        //Create new entry
        Entry entry = new Entry(entryTitle, entryNotes, date, 0);
        //Create new entry content
        EntryContent entryContent = new EntryContent(entryNotes, entryObligations, entryDecisions, entryOutcomes, date, 0);
        //Get database reference
        DatabaseReference entryRef = mDatabase.getReference();
        //Check if entry title is empty, and display an error if it is
        if (!entryTitle.isEmpty()) {
            //Create new entry content array
            ArrayList<String> entryContentsList = new ArrayList<>();
            //Add the generated id to the array
            entryContentsList.add(entryRef.push().getKey());
            //Set the entry's content list
            entry.setEntryContentList(entryContentsList);
            //Get an id for the entry
            String entryID = entryRef.push().getKey();
            // Save entry to database
            entryRef.child("Entries").child(mUser.getDisplayName()).child(mJournalID).child(entryID).setValue(entry);
            entryRef.child("EntryContents").child(mUser.getDisplayName()).child(entryID).child(entryContentsList.get(0)).setValue(entryContent).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Display a success message
                    Toast.makeText(CreateEntryActivity.this, "You have successfully created an entry!", Toast.LENGTH_SHORT).show();
                    //Close the current activity
                    finish();
                }
            });
        } else {
            //Create an error message for the EditText
            entryTitleET.setError("Missing Entry Title!");
        }
    }

    /**
     * Cancel entry handler. If there's edits then show a confirm dialog, otherwise close activity.
     *
     * @param view the view
     */
    @OnClick(R.id.cancelEntryBTN)
    public void cancelEntryHandler(View view) {
        Boolean isEmpty = true;
        //Loop through all elements inside the activity layout
        for (int i = 0; i < createEntryLayout.getChildCount(); i++) {
            //Check if the current element is an EditText
            if (createEntryLayout.getChildAt(i) instanceof EditText) {
                //Set current element as an EditText
                EditText currentET = (EditText) createEntryLayout.getChildAt(i);
                //Check if it's empty, and display a confirm dialog if it isn't
                if (!currentET.getText().toString().isEmpty()) {
                    isEmpty = false;
                    //Initialize & Display dialog
                    new AlertDialog.Builder(CreateEntryActivity.this)
                            .setTitle("Exit Confirmation")
                            .setMessage("Are you sure you want to exit without saving your changes?")
                            .setIcon(R.drawable.warning)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Closes the activity
                                    finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        }
        //If it's empty then exit activity
        if (isEmpty) {
            finish();
        }
    }
}
