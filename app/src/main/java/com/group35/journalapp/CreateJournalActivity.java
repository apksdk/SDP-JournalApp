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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.group35.journalapp.models.Journal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The Create journal activity.
 * <p>
 * Created by Joshua on 9/18/2017
 */
public class CreateJournalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    @BindView(R.id.imagePreviewIV)
    ImageView imagePreviewIV;

    @BindView(R.id.journalTitleET)
    EditText journalTitleET;

    @BindView(R.id.journalDescriptionET)
    EditText journalDescriptionET;

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_journal);
        ButterKnife.bind(this);
        //Initial UI Setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set up Navigation Menu
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
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
     * Cancel Journal handler. If there's edits then show a confirm dialog, otherwise close activity.
     *
     * @param view the view
     */
    @OnClick(R.id.backBTN)
    public void backHandler(View view) {
        confirmExit();
    }

    /**
     * Save handler. Saves the journal on Firebase Database
     *
     * @param view the view
     */
    @OnClick(R.id.saveJournalBTN)
    public void saveHandler(View view) {
        //Initialize progress dialog
        final ProgressDialog saveProgressDialog = new ProgressDialog(this);
        saveProgressDialog.setMessage("Saving Journal...");
        saveProgressDialog.setCancelable(false);
        saveProgressDialog.show();

        //Get journal info
        String journalTitle = journalTitleET.getText().toString();
        String journalDescription = journalDescriptionET.getText().toString();
        //Get current time
        String date = new SimpleDateFormat("dd/MM/yyyy hh:mma", Locale.getDefault()).format(new Date());
        Log.d("Date", date);
        //Create new journal object
        Journal journal = new Journal(journalTitle, journalDescription, "", date);
        //Get database reference
        DatabaseReference journalRef = mDatabase.getReference();
        //Check if the journal's title is empty
        if (!journalTitle.isEmpty()) {
            //Save the journal
            final String journalID = journalRef.push().getKey();
            journalRef.child("users").child(mUser.getDisplayName()).child("Journals").child(journalID).setValue(journal).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Check if the data was saved successfully
                    if (task.isSuccessful()) {
                        //Create a new intent
                        Intent intent = new Intent(CreateJournalActivity.this, ViewEntriesActivity.class);
                        //Pass the journalID to the intent
                        intent.putExtra("journalID", journalID);
                        //Start activity with intent
                        startActivity(intent);
                        //Display success message
                        Toast.makeText(getBaseContext(), "You have successfully created a journal.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        //Display error message
                        Toast.makeText(CreateJournalActivity.this, "There was an error while creating your journal. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                    saveProgressDialog.dismiss();
                }
            });
        } else {
            //Set error
            journalTitleET.setError("Missing Journal Title!");
        }
    }

    /**
     * Confirm if the form does not have any unsaved information and asks the user to confirm before exiting
     */
    private void confirmExit() {
        //Check if both ETs are empty
        if (TextUtils.isEmpty(journalDescriptionET.getText().toString()) && TextUtils.isEmpty(journalTitleET.getText().toString())) {
            //Close the activity
            finish();
        } else {
            //Create & Display an exit confirmation dialog
            new AlertDialog.Builder(CreateJournalActivity.this)
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

    /**
     * Image selector. Does nothing ATM
     *
     * @param view the view
     */
//@OnClick(R.id.imagePreviewIV)
    public void imageSelector(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.coloursSelect, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Context context;
                //String colours[] = context.getResources().getStringArray(R.array.coloursSelect);
                //skeleton code
                switch (position) {
                    case 0:
                        // view.setBackgroundColor(colours[0]);
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:

                        break;
                    case 10:

                        break;
                    case 11:

                        break;
                    case 12:

                        break;
                    case 13:

                        break;
                    case 14:

                        break;
                    case 15:

                        break;
                    case 16:

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
