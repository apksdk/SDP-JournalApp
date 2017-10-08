package com.group35.journalapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
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

    public JournalHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }

    public ImageView getJournalPreviewIV() {
        return mJournalPreviewIV;
    }

    public void setJournalPreviewIV(ImageView mJournalPreviewIV) {
        this.mJournalPreviewIV = mJournalPreviewIV;
    }

    public TextView getJournalTitleTV() {
        return mJournalTitleTV;
    }

    public void setJournalTitleTV(String journalTitle) {
        mJournalTitleTV.setText(journalTitle);
    }

    public TextView getRecordedEntriesTV() {
        return mRecordedEntriesTV;
    }

    public void setRecordedEntriesTV(String recordedEntries) {
        mRecordedEntriesTV.setText(recordedEntries);
    }

    public TextView getDescriptionTV() {
        return mDescriptionTV;
    }

    public void setDescriptionTV(String description) {
        mDescriptionTV.setText(description);
    }

    public TextView getDateTV() {
        return mDateTV;
    }

    public void setDateTV(String date) {
        mDateTV.setText(date);
    }

    @OnClick(R.id.journalsLayout)
    public void journalClickHandler(View view) {
        //Pass some info here as intent
        Intent intent = new Intent(mContext, ViewEntriesActivity.class);
        intent.putExtra("journalID", mJournalID);
        mContext.startActivity(intent);
    }

    public String getJournalID() {
        return mJournalID;
    }

    public void setJournalID(String journalID) {
        mJournalID = journalID;
    }
}
