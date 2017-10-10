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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.group35.journalapp.models.Entry;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewEntriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int RV_SETUP_HIDE_HIDDEN_DELETED = 1;
    private static final int RV_SETUP_SHOW_HIDDEN_DELETED = 2;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    private String mJournalID;
    private boolean mToggleHiddenDeletedEntries;

    private MenuItem clickedItem;

    @BindView(R.id.viewEntriesRV)
    RecyclerView viewEntriesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startCreateEntryIntent = new Intent(ViewEntriesActivity.this, CreateEntryActivity.class);
                startCreateEntryIntent.putExtra("journalID", mJournalID);
                startActivity(startCreateEntryIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        mJournalID = intent.getStringExtra("journalID");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        viewEntriesRV.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        viewEntriesRV.addItemDecoration(dividerItemDecoration);
        setupRecyclerView(RV_SETUP_HIDE_HIDDEN_DELETED);
    }

    private void setupRecyclerView(int setupCode) {
        DatabaseReference entriesRef = mDatabase.getReference().child("Entries").child(mUser.getDisplayName()).child(mJournalID);
        Query entriesQuery;
        if (setupCode == RV_SETUP_SHOW_HIDDEN_DELETED) {
            entriesQuery = entriesRef;
        } else {
            entriesQuery = entriesRef.orderByChild("entryDeleted_Hidden").equalTo("false_false");
        }

        FirebaseRecyclerAdapter<Entry, EntryHolder> mAdapter = new FirebaseRecyclerAdapter<Entry, EntryHolder>(
                Entry.class,
                R.layout.item_journal_entry,
                EntryHolder.class,
                entriesQuery) {
            @Override
            protected void populateViewHolder(EntryHolder viewHolder, Entry model, int position) {
                viewHolder.setEntryTitleTV(model.getEntryTitle());
                viewHolder.setEntryDescriptionTV(model.getEntryPreview());
                viewHolder.setLastModifiedTimeTV(model.getLastModifyDate());
                viewHolder.setEntryID(getRef(position).getKey());
                viewHolder.setJournalID(mJournalID);
                viewHolder.setDeleted(model.isJournalEntryDeleted());
                viewHolder.setHidden(model.isJournalEntryHidden());

                if (!model.getEntryContentList().isEmpty()) {
                    viewHolder.setEntryVersion(model.getEntryContentList().get(model.getEntryContentList().size() - 1));
                }
            }
        };
        viewEntriesRV.setAdapter(mAdapter);
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
        getMenuInflater().inflate(R.menu.view_menu, menu);
        clickedItem = menu.findItem(R.id.action_toggle_view);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            mAuth.signOut();
            startActivity(new Intent(ViewEntriesActivity.this, LoginActivity.class));
        } else if (id == R.id.action_toggle_view) {
            if (mToggleHiddenDeletedEntries) {
                clickedItem.setTitle("Show Hidden/Deleted Entries");
                mToggleHiddenDeletedEntries = false;
                setupRecyclerView(RV_SETUP_HIDE_HIDDEN_DELETED);
            } else {
                clickedItem.setTitle("Hide Hidden/Deleted Entries");
                mToggleHiddenDeletedEntries = true;
                setupRecyclerView(RV_SETUP_SHOW_HIDDEN_DELETED);
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
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
