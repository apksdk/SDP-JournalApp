package com.group35.journalapp;

/**
 * Created by Alex on 5/10/2017.
 */

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

public class EntryHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.EntryTitleTV)
    TextView mEntryTitleTV;

    @BindView(R.id.obligationsTV)
    TextView mObligationsTV;

    @BindView(R.id.decisionsTV)
    TextView mDecisionsTV;

    @BindView(R.id.outcomesTV)
    TextView mOutcomesTV;

    @BindView(R.id.commentsTV)
    TextView mCommentsTV;

    @BindView(R.id.EntriesLayout)
    RelativeLayout mEntriesLayout;

    private Context mContext;

    public EntryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    public TextView getEntryTitleTV() {
        return mEntryTitleTV;
    }

    public void setEntryTitleTV(String entryTitle) {
        mEntryTitleTV.setText(entryTitle);
    }

    public TextView getObligationsTV() {
        return mObligationsTV;
    }

    public void setObligationsTV(String obligations) {
        mObligationsTV.setText(obligations);
    }
    public TextView getDecisionsTV() {
        return mOutcomesTV;
    }

    public void setDecisionsTV(String decisions) {
        mDecisionsTV.setText(decisions);
    }

    public TextView getOutcomesTV() {
        return mOutcomesTV;
    }

    public void setOutcomesTV(String outcomes) {
        mOutcomesTV.setText(outcomes);
    }

    public TextView getCommentsTV() {
        return mCommentsTV;
    }

    public void setCommentsTV(String comments) {
        mCommentsTV.setText(comments);
    }


    @OnClick(R.id.entriesLayout)
    public void entriesClickHandler(View view) {
        Intent intent = new Intent(mContext, ViewEntriesActivity.class);
        intent.putExtra("Entry", "");
        mContext.startActivity(intent);
    }
}