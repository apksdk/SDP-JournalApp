package com.group35.journalapp.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.group35.journalapp.R;
import com.group35.journalapp.ViewEntriesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The Viewholder for individual Journals
 *
 * Created by Joshua on 9/30/2017.
 */
public class JournalHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.journalPreviewIV)
    ImageView mJournalPreviewIV;

    @BindView(R.id.journalTitleTV)
    TextView mJournalTitleTV;

    @BindView(R.id.recordedEntriesTV)
    TextView mRecordedEntriesTV;

    @BindView(R.id.descriptionTV)
    TextView mDescriptionTV;

    @BindView(R.id.dateTV)
    TextView mDateTV;

    @BindView(R.id.journalsLayout)
    RelativeLayout mJournalsLayout;

    private Context mContext;
    private String mJournalID;

    /**
     * Instantiates a new Journal holder.
     *
     * @param itemView the item view
     */
    public JournalHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }

    /**
     * Gets journal preview iv.
     *
     * @return the journal preview iv
     */
    public ImageView getJournalPreviewIV() {
        return mJournalPreviewIV;
    }

    /**
     * Sets journal preview iv.
     *
     * @param mJournalPreviewIV the m journal preview iv
     */
    public void setJournalPreviewIV(ImageView mJournalPreviewIV) {
        this.mJournalPreviewIV = mJournalPreviewIV;
    }

    /**
     * Gets journal title tv.
     *
     * @return the journal title tv
     */
    public TextView getJournalTitleTV() {
        return mJournalTitleTV;
    }

    /**
     * Sets journal title tv.
     *
     * @param journalTitle the journal title
     */
    public void setJournalTitleTV(String journalTitle) {
        mJournalTitleTV.setText(journalTitle);
    }

    /**
     * Gets recorded entries tv.
     *
     * @return the recorded entries tv
     */
    public TextView getRecordedEntriesTV() {
        return mRecordedEntriesTV;
    }

    /**
     * Sets recorded entries tv.
     *
     * @param recordedEntries the recorded entries
     */
    public void setRecordedEntriesTV(String recordedEntries) {
        mRecordedEntriesTV.setText(recordedEntries);
    }

    /**
     * Gets description tv.
     *
     * @return the description tv
     */
    public TextView getDescriptionTV() {
        return mDescriptionTV;
    }

    /**
     * Sets description tv.
     *
     * @param description the description
     */
    public void setDescriptionTV(String description) {
        mDescriptionTV.setText(description);
    }

    /**
     * Gets date tv.
     *
     * @return the date tv
     */
    public TextView getDateTV() {
        return mDateTV;
    }

    /**
     * Sets date tv.
     *
     * @param date the date
     */
    public void setDateTV(String date) {
        mDateTV.setText(date);
    }

    /**
     * Journal click handler. Opens a new activity to display entries.
     *
     * @param view the view
     */
    @OnClick(R.id.journalsLayout)
    public void journalClickHandler(View view) {
        //Create a new intent
        Intent intent = new Intent(mContext, ViewEntriesActivity.class);
        //Pass the journalID to the intent
        intent.putExtra("journalID", mJournalID);
        //Start activity with intent
        mContext.startActivity(intent);
    }

    /**
     * Gets journal id.
     *
     * @return the journal id
     */
    public String getJournalID() {
        return mJournalID;
    }

    /**
     * Sets journal id.
     *
     * @param journalID the journal id
     */
    public void setJournalID(String journalID) {
        mJournalID = journalID;
    }
}
