package com.group35.journalapp.models;

import java.util.ArrayList;

/**
 * Created by Joshua on 9/27/2017.
 */

public class Journal {
    private String mJournalName;
    private String mJournalDescription;
    private ArrayList<JournalEntry> mJournalEntries;

    public Journal (String journalName, String journalDescription) {
        mJournalName = journalName;
        mJournalDescription = journalDescription;
        mJournalEntries = new ArrayList<JournalEntry>();
    }

    public String getJournalName() {
        return mJournalName;
    }

    public void setJournalName(String journalName) {
        this.mJournalName = journalName;
    }

    public ArrayList<JournalEntry> getJournalEntries() {
        return mJournalEntries;
    }

    public void setJournalEntries(ArrayList<JournalEntry> journalEntries) {
        this.mJournalEntries = journalEntries;
    }

    public String getJournalDescription() {
        return mJournalDescription;
    }

    public void setJournalDescription(String journalDescription) {
        this.mJournalDescription = journalDescription;
    }
}
