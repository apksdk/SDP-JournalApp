package com.group35.journalapp.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.group35.journalapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A view holder for displaying individual entry contents
 * <p>
 * Created by Joshua on 10/8/2017.
 */
public class EntryContentHolder extends RecyclerView.ViewHolder {
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

    /**
     * Instantiates a new Entry content holder.
     *
     * @param itemView the item view
     */
    public EntryContentHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }

    /**
     * Gets last modified time tv.
     *
     * @return the last modified time tv
     */
    public TextView getLastModifiedTimeTV() {
        return lastModifiedTimeTV;
    }

    /**
     * Sets last modified time.
     *
     * @param lastModifiedTime the last modified time
     */
    public void setLastModifiedTimeTV(String lastModifiedTime) {
        lastModifiedTimeTV.setText(lastModifiedTime);
    }

    /**
     * Gets entry version tv.
     *
     * @return the entry version tv
     */
    public TextView getEntryVersionTV() {
        return entryVersionTV;
    }

    /**
     * Sets entry version.
     *
     * @param entryVersion the entry version
     */
    public void setEntryVersionTV(String entryVersion) {
        entryVersionTV.setText(entryVersion);
    }

    /**
     * Gets entry changes note tv.
     *
     * @return the entry changes note tv
     */
    public TextView getEntryChangesNoteTV() {
        return entryChangesNoteTV;
    }

    /**
     * Sets entry changes note.
     *
     * @param entryChangesNote the entry changes note
     */
    public void setEntryChangesNoteTV(String entryChangesNote) {
        entryChangesNoteTV.setText(entryChangesNote);
    }

    /**
     * Gets entry obligations tv.
     *
     * @return the entry obligations tv
     */
    public TextView getEntryObligationsTV() {
        return entryObligationsTV;
    }

    /**
     * Sets entry obligations.
     *
     * @param entryObligations the entry obligations
     */
    public void setEntryObligationsTV(String entryObligations) {
        entryObligationsTV.setText(entryObligations);
    }

    /**
     * Gets entry decisions tv.
     *
     * @return the entry decisions tv
     */
    public TextView getEntryDecisionsTV() {
        return entryDecisionsTV;
    }

    /**
     * Sets entry decisions.
     *
     * @param entryDecisions the entry decisions
     */
    public void setEntryDecisionsTV(String entryDecisions) {
        entryDecisionsTV.setText(entryDecisions);
    }

    /**
     * Gets entry outcome tv.
     *
     * @return the entry outcome tv
     */
    public TextView getEntryOutcomeTV() {
        return entryOutcomeTV;
    }

    /**
     * Sets entry outcome.
     *
     * @param entryOutcome the entry outcome
     */
    public void setEntryOutcomeTV(String entryOutcome) {
        entryOutcomeTV.setText(entryOutcome);
    }
}
