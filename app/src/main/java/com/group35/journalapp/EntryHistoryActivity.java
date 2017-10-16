package com.group35.journalapp;

import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.group35.journalapp.models.EntryContent;
import com.group35.journalapp.viewholders.EntryContentHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Entry history activity.
 * <p>
 * Created by Joshua on 9/18/2017
 */
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

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_history);
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

        //Setup Progress Dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Entry History...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Get intent & data
        Intent intent = getIntent();
        mEntryID = intent.getStringExtra("entryID");
        mEntryTitle = intent.getStringExtra("entryTitle");
        //Setup RecyclerView
        entryHistoryRV.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        entryHistoryRV.addItemDecoration(dividerItemDecoration);

        DatabaseReference entryHistoryRef = mDatabase.getReference().child("EntryContents").child(mUser.getDisplayName()).child(mEntryID);
        //Create new Adapter for RV
        FirebaseRecyclerAdapter<EntryContent, EntryContentHolder> mAdapter = new FirebaseRecyclerAdapter<EntryContent, EntryContentHolder>(
                EntryContent.class,
                R.layout.item_entry_history,
                EntryContentHolder.class,
                entryHistoryRef) {
            @Override
            public void onDataChanged() {
                progressDialog.dismiss();
                super.onDataChanged();
            }

            @Override
            protected void populateViewHolder(EntryContentHolder viewHolder, EntryContent model, int position) {
                //Set up view holder
                viewHolder.setLastModifiedTimeTV(model.getEntryModifiedDate());
                viewHolder.setEntryVersionTV("Entry Version: " + model.getEntryVersion());
                viewHolder.setEntryObligationsTV("Obligations: \n" + model.getEntryObligations());
                viewHolder.setEntryDecisionsTV("Decisions: \n" + model.getEntryDecisions());
                viewHolder.setEntryOutcomeTV("Outcome: \n" + model.getEntryOutcomes());
                viewHolder.setEntryChangesNoteTV("Notes: \n" + model.getEntryNotes());
            }
        };
        //Set adapter
        entryHistoryRV.setAdapter(mAdapter);
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
