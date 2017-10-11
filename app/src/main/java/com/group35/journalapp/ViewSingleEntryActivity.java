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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group35.journalapp.models.EntryContent;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * The type View single entry activity.
 * <p>
 * Created by Joshua on 9/18/2017
 */
public class ViewSingleEntryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    private String mEntryID;
    private String mEntryTitle;
    private String mEntryDescription;
    private String mEntryVersion;
    private String mJournalID;

    @BindView(R.id.obligationsContentTV)
    TextView obligationsContentTV;

    @BindView(R.id.outcomeContentTV)
    TextView outcomeContentTV;

    @BindView(R.id.notesContentTV)
    TextView notesContentTV;

    @BindView(R.id.decisionsContentTV)
    TextView decisionsContentTV;

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_entry);
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
        mEntryTitle = intent.getStringExtra("entryTitle");
        mEntryDescription = intent.getStringExtra("entryDescription");
        mEntryVersion = intent.getStringExtra("entryVersion");
        mJournalID = intent.getStringExtra("journalID");

        DatabaseReference entryContentRef = mDatabase.getReference().child("EntryContents")
                .child(mUser.getDisplayName()).child(mEntryID).child(mEntryVersion);

        entryContentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EntryContent entryContent = dataSnapshot.getValue(EntryContent.class);
                obligationsContentTV.setText(entryContent.getEntryObligations());
                outcomeContentTV.setText(entryContent.getEntryOutcomes());
                notesContentTV.setText(entryContent.getEntryNotes());
                decisionsContentTV.setText(entryContent.getEntryDecisions());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            super.onBackPressed();
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
        getMenuInflater().inflate(R.menu.view_single_entry, menu);
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

        //Check which option is selected
        if (id == R.id.action_edit_entry) {
            //Get entry content
            String obligations = obligationsContentTV.getText().toString();
            String decisions = decisionsContentTV.getText().toString();
            String outcome = outcomeContentTV.getText().toString();
            String notes = notesContentTV.getText().toString();
            //Create new intent
            Intent editEntryIntent = new Intent(ViewSingleEntryActivity.this, EditSingleEntryActivity.class);
            //Save entry content into the intent
            editEntryIntent.putExtra("entryID", mEntryID);
            editEntryIntent.putExtra("entryObligations", obligations);
            editEntryIntent.putExtra("entryDecisions", decisions);
            editEntryIntent.putExtra("entryOutcome", outcome);
            editEntryIntent.putExtra("entryNotes", notes);
            editEntryIntent.putExtra("entryVersion", mEntryVersion);
            editEntryIntent.putExtra("journalID", mJournalID);
            //Start edit entry activity
            startActivity(editEntryIntent);
        } else if( id == R.id.action_view_history) {
            //Create intent & save relevant data before starting activity
            Intent entryHistoryIntent = new Intent(ViewSingleEntryActivity.this, EntryHistoryActivity.class);
            entryHistoryIntent.putExtra("entryID", mEntryID);
            entryHistoryIntent.putExtra("entryTitle", mEntryTitle);
            startActivity(entryHistoryIntent);
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
}