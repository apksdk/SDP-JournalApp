package com.group35.journalapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joshua on 10/8/2017.
 */

class EntryContentHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.lastModifiedTimeTV)
    TextView lastModifiedTimeTV;

    @BindView(R.id.entryVersionTV)
    TextView entryVersionTV;

    @BindView(R.id.entryChangesNoteTV)
    TextView entryChangesNoteTV;

    @BindView(R.id.entryObligationsTV)
    TextView entryObligationsTV;

    @BindView(R.id.entryDecisionsTV)
    TextView entryDecisionsTV;

    @BindView(R.id.entryOutcomeTV)
    TextView entryOutcomeTV;

    private Context mContext;
    private String mEntryID;
    private int mEntryVersion;

    public EntryContentHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }

    public TextView getLastModifiedTimeTV() {
        return lastModifiedTimeTV;
    }

    public void setLastModifiedTimeTV(String lastModifiedTime) {
        lastModifiedTimeTV.setText(lastModifiedTime);
    }

    public TextView getEntryVersionTV() {
        return entryVersionTV;
    }

    public void setEntryVersionTV(String entryVersion) {
        entryVersionTV.setText(entryVersion);
    }

    public TextView getEntryChangesNoteTV() {
        return entryChangesNoteTV;
    }

    public void setEntryChangesNoteTV(String entryChangesNote) {
        entryChangesNoteTV.setText(entryChangesNote);
    }

    public TextView getEntryObligationsTV() {
        return entryObligationsTV;
    }

    public void setEntryObligationsTV(String entryObligations) {
        entryObligationsTV.setText(entryObligations);
    }

    public TextView getEntryDecisionsTV() {
        return entryDecisionsTV;
    }

    public void setEntryDecisionsTV(String entryDecisions) {
        entryDecisionsTV.setText(entryDecisions);
    }

    public TextView getEntryOutcomeTV() {
        return entryOutcomeTV;
    }

    public void setEntryOutcomeTV(String entryOutcome) {
        entryOutcomeTV.setText(entryOutcome);
    }
}
