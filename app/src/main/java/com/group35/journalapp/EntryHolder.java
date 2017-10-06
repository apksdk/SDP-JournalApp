package com.group35.journalapp;

/**
 * Created by Alex on 5/10/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntryHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.lastModifiedTimeTV)
    TextView mLastModifiedTimeTV;

    @BindView(R.id.entryDescriptionTV)
    TextView mEntryDescriptionTV;

    @BindView(R.id.entryTitleTV)
    TextView mEntryTitleTV;

    private Context mContext;
    private String mEntryID;
    private String mEntryVersion;

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
}