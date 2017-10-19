package com.group35.journalapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


/**
 * The Edit single entry activity.
 * <p>
 * Created by Joshua on 9/18/2017
 */
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
    private String mEntryTitle;

    @BindView(R.id.obligationsContentET)
    EditText obligationsContentET;

    @BindView(R.id.outcomeContentET)
    EditText outcomeContentET;

    @BindView(R.id.notesContentET)
    EditText notesContentET;

    @BindView(R.id.decisionsContentET)
    EditText decisionsContentET;

    @BindView(R.id.editEntryLayout)
    RelativeLayout editEntryLayout;

    @BindView(R.id.entryTitleTV)
    TextView entryTitleTV;

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_entry);
        ButterKnife.bind(this);
        //Setup UI
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //Get intent & data
        Intent intent = getIntent();
        mEntryID = intent.getStringExtra("entryID");
        mEntryTitle = intent.getStringExtra("entryTitle");
        mEntryObligations = intent.getStringExtra("entryObligations");
        mEntryDecisions = intent.getStringExtra("entryDecisions");
        mEntryOutcome = intent.getStringExtra("entryOutcome");
        mEntryNotes = intent.getStringExtra("entryNotes");
        mEntryVersion = intent.getStringExtra("entryVersion");
        mJournalID = intent.getStringExtra("journalID");
        //Setup UI Views
        entryTitleTV.setText(mEntryTitle);
        obligationsContentET.setText(mEntryObligations);
        decisionsContentET.setText(mEntryDecisions);
        outcomeContentET.setText(mEntryOutcome);
        notesContentET.setText(mEntryNotes);
    }

    /**
     * Closes the navigation drawer if it's open, otherwise exit the activity
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            confirmExit();
        }
    }

    /**
     * Confirm if the form does not have any unsaved information and asks the user to confirm before exiting
     */
    private void confirmExit() {
        Boolean isEmpty = true;
        //Loop through all elements inside the activity layout
        for (int i = 0; i < editEntryLayout.getChildCount(); i++) {
            //Check if the current element is an EditText
            if (editEntryLayout.getChildAt(i) instanceof EditText) {
                //Set current element as an EditText
                EditText currentET = (EditText) editEntryLayout.getChildAt(i);
                //Check if it's empty, and display a confirm dialog if it isn't
                if (!currentET.getText().toString().isEmpty()) {
                    isEmpty = false;
                }
            }
        }
        //If it's empty then exit activity
        if (isEmpty) {
            finish();
        } else {
            //Initialize & Display dialog
            new AlertDialog.Builder(EditSingleEntryActivity.this)
                    .setTitle("Exit Confirmation")
                    .setMessage("Are you sure you want to exit without making changes?")
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

    /**
     * Inflate the menu
     *
     * @param menu The menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_single_entry, menu);
        return true;
    }


    /**
     * Performs action depending on the clicked menu item
     *
     * @param item Selected menu item
     * @return selected item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            //Initialize progress dialog
            final ProgressDialog saveProgressDialog = new ProgressDialog(this);
            saveProgressDialog.setMessage("Saving Journal...");
            saveProgressDialog.setCancelable(false);
            //Get all data from edit texts
            final String newObligations = obligationsContentET.getText().toString();
            final String newDecisions = decisionsContentET.getText().toString();
            final String newOutcome = outcomeContentET.getText().toString();
            final String newNotes = notesContentET.getText().toString();
            //Check if all fields have not been modified
            if (newObligations.equals(mEntryObligations) && newDecisions.equals(mEntryDecisions)
                    && newOutcome.equals(mEntryOutcome) && newNotes.equals(mEntryNotes)) {
                //Display an error on no changes
                Toast.makeText(EditSingleEntryActivity.this, "You have not made any changes.", Toast.LENGTH_SHORT).show();
            } else {
                saveProgressDialog.show();
                final ArrayList<String> entryContentList = new ArrayList<>();
                //Get database reference
                final DatabaseReference entryRef = mDatabase.getReference().child("Entries").child(mUser.getDisplayName())
                        .child(mJournalID).child(mEntryID).child("entryContentList");
                //Get a new entry content key
                final String newEntry = entryRef.push().getKey();
                // Get list of entry contents
                entryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get all entry contents from the array list
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            entryContentList.add(snapshot.getValue(String.class));
                        }
                        //Get current time
                        String date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssa", Locale.getDefault()).format(new Date());
                        //Create new entry content
                        EntryContent entryContent = new EntryContent(newNotes, newObligations, newDecisions, newOutcome, date, entryContentList.size());
                        //Add the entry to the array
                        entryContentList.add(newEntry);
                        //Save the new array list
                        entryRef.setValue(entryContentList);
                        // Get database reference
                        DatabaseReference journalDateRef = mDatabase.getReference().child("users").child(mUser.getDisplayName())
                                .child("Journals").child(mJournalID).child("journalModifiedDate");
                        //Set new last modified date
                        journalDateRef.setValue(date);
                        // Update entry description
                        DatabaseReference entryDescriptionRef = mDatabase.getReference().child("Entries").child(mUser.getDisplayName())
                                .child(mJournalID).child(mEntryID).child("entryPreview");
                        entryDescriptionRef.setValue(newNotes);
                        // Update entry contents
                        DatabaseReference entryContentRef = mDatabase.getReference().child("EntryContents").child(mUser.getDisplayName())
                                .child(mEntryID).child(newEntry);
                        entryContentRef.setValue(entryContent).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Display a success message
                                    Toast.makeText(EditSingleEntryActivity.this, "You have successfully modified the entry!", Toast.LENGTH_SHORT).show();
                                    //Close the current activity
                                    finish();
                                } else {
                                    Toast.makeText(EditSingleEntryActivity.this, "There was an error while attempting to save your changes. Please try again.", Toast.LENGTH_LONG).show();
                                }
                                saveProgressDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Do nothing
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Performs action when navigation menu item is clicked
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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


}