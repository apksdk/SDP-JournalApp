package com.group35.journalapp.models;

import java.util.ArrayList;

/**
 * Created by Joshua on 9/27/2017.
 */

public class Journal {
    private String mJournalName;
    private String mJournalDescription;
    private ArrayList<JournalEntry> mJournalEntries;
    private String mJournalModifiedDate;
    private String mJournalImageLink;

    public Journal() {

    }

    public Journal (String journalName, String journalDescription, String journalImage) {
        mJournalName = journalName;
        mJournalDescription = journalDescription;
        mJournalImageLink = journalImage;
        mJournalEntries = new ArrayList<JournalEntry>();
        mJournalModifiedDate = "04/12/1991";
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

    public String getJournalModifiedDate() {
        return mJournalModifiedDate;
    }

    public void setJournalModifiedDate(String mJournalDate) {
        this.mJournalModifiedDate = mJournalDate;
    }

    public String getJournalImageLink() {
        return mJournalImageLink;
    }

    public void setJournalImageLink(String mJournalImageLink) {
        this.mJournalImageLink = mJournalImageLink;
    }
}
