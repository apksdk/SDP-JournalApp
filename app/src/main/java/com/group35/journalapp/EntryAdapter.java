package com.group35.journalapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group35.journalapp.models.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Joshua on 10/15/2017.
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private ArrayList<Entry> mEntryList;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lastModifiedTimeTV)
        TextView mLastModifiedTimeTV;

        @BindView(R.id.entryDescriptionTV)
        TextView mEntryDescriptionTV;

        @BindView(R.id.entryTitleTV)
        TextView mEntryTitleTV;

        private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        private FirebaseAuth mAuth = FirebaseAuth.getInstance();
        private FirebaseUser mUser = mAuth.getCurrentUser();

        private String mEntryID;
        private String mEntryVersion;
        private String mJournalID;
        private boolean mDeleted;
        private boolean mHidden;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
            if (mHidden || mDeleted) {
                Toast.makeText(mContext, "This entry has already been marked as deleted/hidden.", Toast.LENGTH_SHORT).show();
                return false;
            }
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

    public EntryAdapter(Context context, ArrayList<Entry> entryList) {
        mContext = context;
        mEntryList = entryList;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal_entry, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *                   item at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Entry model = mEntryList.get(position);
        //Initialize the entry's view holder
        viewHolder.mEntryTitleTV.setText(model.getEntryTitle());
        if (!model.getEntryPreview().isEmpty()) {
            viewHolder.mEntryDescriptionTV.setText(model.getEntryPreview());
        }
        viewHolder.mLastModifiedTimeTV.setText(model.getLastModifyDate());
        viewHolder.setEntryID(model.getEntryID());
        viewHolder.setJournalID(model.getJournalID());
        viewHolder.setDeleted(model.isJournalEntryDeleted());
        viewHolder.setHidden(model.isJournalEntryHidden());

        if (!model.getEntryContentList().isEmpty()) {
            viewHolder.setEntryVersion(model.getEntryContentList().get(model.getEntryContentList().size() - 1));
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mEntryList.size();
    }
}
