package com.group35.journalapp.viewholders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group35.journalapp.R;
import com.group35.journalapp.ViewSingleEntryActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * A Viewholder class for individual Entries
 * <p>
 * Created by Alex on 5/10/2017.
 * Modified by Josh.
 */
public class EntryHolder extends RecyclerView.ViewHolder {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    @BindView(R.id.lastModifiedTimeTV)
    TextView mLastModifiedTimeTV;

    @BindView(R.id.entryDescriptionTV)
    TextView mEntryDescriptionTV;

    @BindView(R.id.entryTitleTV)
    TextView mEntryTitleTV;

    private Context mContext;
    private String mEntryID;
    private String mEntryVersion;
    private String mJournalID;
    private boolean mDeleted;
    private boolean mHidden;

    /**
     * Instantiates a new Entry holder.
     *
     * @param itemView the item view
     */
    public EntryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }

    /**
     * Entries click handler. Passes information to view individual entries
     *
     * @param view the view
     */
    @OnClick(R.id.entryLayout)
    public void entriesClickHandler(View view) {
        Intent intent = new Intent(mContext, ViewSingleEntryActivity.class);
        intent.putExtra("entryID", mEntryID);
        intent.putExtra("entryTitle", mEntryTitleTV.getText().toString());
        intent.putExtra("entryDescription", mEntryDescriptionTV.getText().toString());
        intent.putExtra("entryVersion", mEntryVersion);
        intent.putExtra("journalID", mJournalID);
        mContext.startActivity(intent);
    }

    /**
     * Gets last modified time tv.
     *
     * @return the last modified time tv
     */
    public TextView getLastModifiedTimeTV() {
        return mLastModifiedTimeTV;
    }

    /**
     * Sets last modified time.
     *
     * @param lastModifiedTime the last modified time
     */
    public void setLastModifiedTimeTV(String lastModifiedTime) {
        this.mLastModifiedTimeTV.setText(lastModifiedTime);
    }

    /**
     * Gets entry description tv.
     *
     * @return the entry description tv
     */
    public TextView getEntryDescriptionTV() {
        return mEntryDescriptionTV;
    }

    /**
     * Sets entry description.
     *
     * @param entryDescription the entry description
     */
    public void setEntryDescriptionTV(String entryDescription) {
        this.mEntryDescriptionTV.setText(entryDescription);
    }

    /**
     * Gets entry title tv.
     *
     * @return the entry title tv
     */
    public TextView getEntryTitleTV() {
        return mEntryTitleTV;
    }

    /**
     * Sets entry title.
     *
     * @param entryTitle the entry title
     */
    public void setEntryTitleTV(String entryTitle) {
        this.mEntryTitleTV.setText(entryTitle);
    }

    /**
     * Sets entry id.
     *
     * @param mEntryID the m entry id
     */
    public void setEntryID(String mEntryID) {
        this.mEntryID = mEntryID;
    }

    /**
     * Gets entry version.
     *
     * @return the entry version
     */
    public String getEntryVersion() {
        return mEntryVersion;
    }

    /**
     * Sets entry version.
     *
     * @param mEntryVersion the m entry version
     */
    public void setEntryVersion(String mEntryVersion) {
        this.mEntryVersion = mEntryVersion;
    }

    /**
     * Sets journal id.
     *
     * @param mJournalID the m journal id
     */
    public void setJournalID(String mJournalID) {
        this.mJournalID = mJournalID;
    }

    /**
     * Create a option menu where the users can hide or delete entries.
     *
     * @param view Entry Layout
     * @return false
     */
    @OnLongClick(R.id.entryLayout)
    public boolean longClickHandler(View view) {
        //Initialize the options list
        CharSequence entryOptions[] = {"Delete Entry", "Hide Entry"};
        //Initialize & display a dialog for the options
        new AlertDialog.Builder(mContext)
                .setTitle("Select an Option")
                .setItems(entryOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Get a database reference
                        final DatabaseReference ref = mDatabase.getReference().child("Entries").child(mUser.getDisplayName()).child(mJournalID).child(mEntryID);
                        //Check which option the user has chosen
                        if (i == 0) {
                            //Delete Entry
                            deleteEntry(ref);
                        } else if (i == 1) {
                            //Hide Entry
                            String entryDeleted_Hidden = mDeleted + "_" + true;
                            //Create a map to store data values
                            Map journalUpdate = new HashMap();
                            journalUpdate.put("journalEntryHidden", true);
                            journalUpdate.put("entryDeleted_Hidden", entryDeleted_Hidden);
                            //Update the current entry
                            ref.updateChildren(journalUpdate);
                        }
                    }
                })
                .show();
        return false;
    }

    /**
     * "Deletes" an entry & updates it in the database
     *
     * @param ref The database reference to the selected entry
     */
    private void deleteEntry(final DatabaseReference ref) {
        //Create a new dialog & show to user
        new AlertDialog.Builder(mContext)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this entry?")
                .setIcon(R.drawable.warning)
                .setCancelable(false)
                .setPositiveButton("Delete Entry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Delete Entry
                        String entryDeleted_Hidden = true + "_" + mHidden;
                        Map journalUpdate = new HashMap();
                        journalUpdate.put("journalEntryDeleted", true);
                        journalUpdate.put("entryDeleted_Hidden", entryDeleted_Hidden);
                        //Update entry
                        ref.updateChildren(journalUpdate);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Closes the dialog without further actions
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


    /**
     * Deleted boolean.
     *
     * @return whether the entry is marked as deleted
     */
    public boolean deleted() {
        return mDeleted;
    }

    /**
     * Sets deleted status.
     *
     * @param mDeleted the deletion status of the entry
     */
    public void setDeleted(boolean mDeleted) {
        this.mDeleted = mDeleted;
    }

    /**
     * Hidden boolean.
     *
     * @return whether the entry is marked as hidden
     */
    public boolean hidden() {
        return mHidden;
    }

    /**
     * Sets hidden status.
     *
     * @param mHidden the hidden status of the entry
     */
    public void setHidden(boolean mHidden) {
        this.mHidden = mHidden;
    }
}