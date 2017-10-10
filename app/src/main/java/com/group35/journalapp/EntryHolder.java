package com.group35.journalapp;

/**
 * Created by Alex on 5/10/2017.
 * Modified by Josh.
 */

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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

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

    public EntryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }

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

    public TextView getLastModifiedTimeTV() {
        return mLastModifiedTimeTV;
    }

    public void setLastModifiedTimeTV(String lastModifiedTime) {
        this.mLastModifiedTimeTV.setText(lastModifiedTime);
    }

    public TextView getEntryDescriptionTV() {
        return mEntryDescriptionTV;
    }

    public void setEntryDescriptionTV(String entryDescription) {
        this.mEntryDescriptionTV.setText(entryDescription);
    }

    public TextView getEntryTitleTV() {
        return mEntryTitleTV;
    }

    public void setEntryTitleTV(String entryTitle) {
        this.mEntryTitleTV.setText(entryTitle);
    }

    public void setEntryID(String mEntryID) {
        this.mEntryID = mEntryID;
    }

    public String getEntryVersion() {
        return mEntryVersion;
    }

    public void setEntryVersion(String mEntryVersion) {
        this.mEntryVersion = mEntryVersion;
    }

    public void setJournalID(String mJournalID) {
        this.mJournalID = mJournalID;
    }

    @OnLongClick(R.id.entryLayout)
    public boolean longClickHandler(View view) {
        //https://www.youtube.com/watch?v=sKFLI5FOOHs&feature=youtu.be&t=612
        CharSequence entryOptions[] = {"Delete Entry", "Hide Entry"};
        new AlertDialog.Builder(mContext)
                .setTitle("Select an Option")
                .setItems(entryOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DatabaseReference ref = mDatabase.getReference().child("Entries").child(mUser.getDisplayName()).child(mJournalID).child(mEntryID);
                        if (i == 0) {
                            deleteEntry(ref);
                        } else if (i == 1) {
                            //Hide Entry
                            String entryDeleted_Hidden = mDeleted + "_" + true;
                            Map journalUpdate = new HashMap();
                            journalUpdate.put("journalEntryHidden", true);
                            journalUpdate.put("entryDeleted_Hidden", entryDeleted_Hidden);
                            ref.updateChildren(journalUpdate);
                        }
                    }
                })
                .show();
        return false;
    }

    private void deleteEntry(final DatabaseReference ref) {
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
                        ref.updateChildren(journalUpdate);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


    public boolean deleted() {
        return mDeleted;
    }

    public void setDeleted(boolean mDeleted) {
        this.mDeleted = mDeleted;
    }

    public boolean hidden() {
        return mHidden;
    }

    public void setHidden(boolean mHidden) {
        this.mHidden = mHidden;
    }
}