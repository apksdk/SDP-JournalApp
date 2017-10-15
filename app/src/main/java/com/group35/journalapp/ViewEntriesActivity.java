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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.group35.journalapp.models.Entry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type View entries activity.
 * <p>
 * Created by Joshua on 9/18/2017
 */
public class ViewEntriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int RV_SETUP_HIDE_HIDDEN_DELETED = 1;
    private static final int RV_SETUP_SHOW_HIDDEN_DELETED = 2;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    private String mJournalID;
    private boolean mToggleHiddenDeletedEntries;
    private EntryAdapter mAdapter;
    private ArrayList<Entry> mEntryList = new ArrayList<>();

    private MenuItem clickedItem;
    private MenuItem searchItem;
    private SearchView searchView;

    @BindView(R.id.noEntriesHintTV)
    TextView noEntriesHintTV;

    @BindView(R.id.viewEntriesRV)
    RecyclerView viewEntriesRV;

    /**
     * The onCreate method. Initializes the activity.
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);
        ButterKnife.bind(this);
        // UI Setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Opens a new activity to create an entry
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startCreateEntryIntent = new Intent(ViewEntriesActivity.this, CreateEntryActivity.class);
                startCreateEntryIntent.putExtra("journalID", mJournalID);
                startActivity(startCreateEntryIntent);
            }
        });
        //Nav menu initialization
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Get intent & data
        Intent intent = getIntent();
        mJournalID = intent.getStringExtra("journalID");
        //Initialize recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        viewEntriesRV.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        viewEntriesRV.addItemDecoration(dividerItemDecoration);
        mAdapter = new EntryAdapter(this, mEntryList);
        viewEntriesRV.setAdapter(mAdapter);

        setupRecyclerView(RV_SETUP_HIDE_HIDDEN_DELETED, false);
    }

    /**
     * Setup the recyclerview depending on the selected option (ie show all or active only)
     *
     * @param setupCode Show either all or active entries
     */
    private void setupRecyclerView(final int setupCode, final boolean filteredKeywords) {
        noEntriesHintTV.setVisibility(View.VISIBLE);

        DatabaseReference entriesRef = mDatabase.getReference().child("Entries").child(mUser.getDisplayName()).child(mJournalID);
        Query entriesQuery;
        //Check the user's selected option
        if (setupCode == RV_SETUP_SHOW_HIDDEN_DELETED) {
            //Displays all entries
            entriesQuery = entriesRef;
        } else {
            //Filters entries that are not hidden/deleted
            entriesQuery = entriesRef.orderByChild("entryDeleted_Hidden").equalTo("false_false");
        }
        //Setup RecyclerView's contents
        entriesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEntryList.clear();

                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    Entry entry = entrySnapshot.getValue(Entry.class);
                    entry.setEntryID(entrySnapshot.getKey());
                    entry.setJournalID(mJournalID);
                    //Get search terms
                    String keywords[] = searchView.getQuery().toString().trim().toLowerCase().split("\\s+");
                    //Check if filtering is on and there's text inside the search box
                    if (filteredKeywords && !(keywords.length == 0)) {
                        //Check if entry title contains the keyword
                        boolean matchFound = false;
                        for(String keyword : keywords ) {
                            if (entry.getEntryTitle().toLowerCase().contains(keyword)) {
                                matchFound = true;
                            }
                        }

                        if(!matchFound) {
                            continue;
                        }
                        mEntryList.add(entry);
                    } else {
                        //Add entry to array
                        mEntryList.add(entry);
                    }
                }
                mAdapter.notifyDataSetChanged();
                //Hide no entry hint if it's visible
                if (!mEntryList.isEmpty()) {
                    noEntriesHintTV.setVisibility(View.GONE);
                }
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
        getMenuInflater().inflate(R.menu.view_entries_menu, menu);
        // Initialize variables
        clickedItem = menu.findItem(R.id.action_toggle_view);
        searchItem = (MenuItem) menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        // Setup the listeners for searching
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Clear focus so the method doesn't run twice
                searchView.clearFocus();
                //Check hidden/delete status
                if (mToggleHiddenDeletedEntries) {
                    setupRecyclerView(RV_SETUP_SHOW_HIDDEN_DELETED, true);
                } else {
                    setupRecyclerView(RV_SETUP_HIDE_HIDDEN_DELETED, true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        View closeButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Clear query
                searchView.setQuery("", false);
                //Check hidden/delete status
                if (mToggleHiddenDeletedEntries) {
                    setupRecyclerView(RV_SETUP_SHOW_HIDDEN_DELETED, false);
                } else {
                    setupRecyclerView(RV_SETUP_HIDE_HIDDEN_DELETED, false);
                }
                //Collapse View
                searchView.onActionViewCollapsed();
                //Collapse Item
                searchItem.collapseActionView();
            }
        });
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
            startActivity(new Intent(ViewEntriesActivity.this, LoginActivity.class));
            finish();
        } else if (id == R.id.action_toggle_view) {
            if (mToggleHiddenDeletedEntries) {
                clickedItem.setTitle("Show Hidden/Deleted Entries");
                mToggleHiddenDeletedEntries = false;
                setupRecyclerView(RV_SETUP_HIDE_HIDDEN_DELETED, false);
            } else {
                clickedItem.setTitle("Hide Hidden/Deleted Entries");
                mToggleHiddenDeletedEntries = true;
                setupRecyclerView(RV_SETUP_SHOW_HIDDEN_DELETED, false);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_journals) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
