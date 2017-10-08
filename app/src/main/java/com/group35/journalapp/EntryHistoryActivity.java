package com.group35.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.group35.journalapp.models.EntryContent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EntryHistoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();

    private String mEntryID;
    private String mEntryTitle;

    @BindView(R.id.entryHistoryRV)
    RecyclerView entryHistoryRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_history);
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

        entryHistoryRV.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        entryHistoryRV.addItemDecoration(dividerItemDecoration);

        DatabaseReference entryHistoryRef = mDatabase.getReference().child("EntryContents").child(mUser.getDisplayName()).child(mEntryID);
        FirebaseRecyclerAdapter<EntryContent, EntryContentHolder> mAdapter = new FirebaseRecyclerAdapter<EntryContent, EntryContentHolder>(
                EntryContent.class,
                R.layout.item_entry_history,
                EntryContentHolder.class,
                entryHistoryRef) {
            @Override
            protected void populateViewHolder(EntryContentHolder viewHolder, EntryContent model, int position) {
                viewHolder.setLastModifiedTimeTV(model.getEntryModifiedDate());
                viewHolder.setEntryVersionTV("Entry Version: " + model.getEntryVersion());
                viewHolder.setEntryObligationsTV("Obligations: \n" + model.getEntryObligations());
                viewHolder.setEntryDecisionsTV("Decisions: \n" + model.getEntryDecisions());
                viewHolder.setEntryOutcomeTV("Outcome: \n" + model.getEntryOutcomes());
                viewHolder.setEntryChangesNoteTV("Notes: \n" + model.getEntryNotes());
            }
        };
        entryHistoryRV.setAdapter(mAdapter);
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
}
