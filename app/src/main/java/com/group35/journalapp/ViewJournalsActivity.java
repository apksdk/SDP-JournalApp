package com.group35.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group35.journalapp.models.Journal;
import com.group35.journalapp.viewholders.JournalHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type View journals activity.
 * <p>
 * Created by Joshua on 9/18/2017
 */
public class ViewJournalsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    @BindView(R.id.viewJournalsRV)
    RecyclerView viewJournalsRV;

    @BindView(R.id.noJournalHintTV)
    TextView noJournalHintTV;

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journals);
        ButterKnife.bind(this);
        //Setup UI
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Opens a new activity to create a journal
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewJournalsActivity.this, CreateJournalActivity.class));
            }
        });
        //Nav menu setup
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Setup RecyclerView
        viewJournalsRV.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        viewJournalsRV.addItemDecoration(dividerItemDecoration);
        //Get database reference for the journal locations
        final DatabaseReference journalsRef = mDatabase.getReference().child("users").child(mUser.getDisplayName()).child("Journals");
        FirebaseRecyclerAdapter<Journal, JournalHolder> mAdapter = new FirebaseRecyclerAdapter<Journal, JournalHolder>(
                Journal.class,
                R.layout.item_journal,
                JournalHolder.class,
                journalsRef) {
            @Override
            protected void populateViewHolder(JournalHolder viewHolder, Journal model, int position) {
                //Hide no journal hint if it's visible
                if(noJournalHintTV.getVisibility() == View.VISIBLE) {
                    noJournalHintTV.setVisibility(View.INVISIBLE);
                }

                //Initialize Glide's loading options
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.icon);
                //Load image using Glide
                Glide.with(ViewJournalsActivity.this)
                        .setDefaultRequestOptions(requestOptions)
                        .load(model.getJournalImageLink())
                        .into(viewHolder.getJournalPreviewIV());
                //Setup view holder
                viewHolder.setJournalTitleTV(model.getJournalName());
                viewHolder.setDescriptionTV(model.getJournalDescription());
                viewHolder.setDateTV(model.getJournalModifiedDate());
                viewHolder.setJournalID(getRef(position).getKey());
                if (model.getJournalEntries() != null) {
                    viewHolder.setRecordedEntriesTV(String.valueOf(model.getJournalEntries().size()));
                }
            }
        };
        viewJournalsRV.setAdapter(mAdapter);
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
        getMenuInflater().inflate(R.menu.view_journals_menu, menu);
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
        if (id == R.id.action_sign_out) {
            mAuth.signOut();
            startActivity(new Intent(ViewJournalsActivity.this, LoginActivity.class));
            finish();
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
            // Do nothing since user is already on this activity
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
