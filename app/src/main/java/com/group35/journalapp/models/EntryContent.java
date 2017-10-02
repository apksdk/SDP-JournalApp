package com.group35.journalapp.models;

/**
 * Created by Josh on 28/09/2017.
 */

public class EntryContent {
    private String mEntryID;
    private String mEntryAuthor;
    private String mEntryTitle;
    private int mEntryVersion;
    private String mEntryNotes;
    private String mEntryObligations;
    private String mEntryDecisions;
    private String mEntryOutcomes;

    public EntryContent() {

    }

    //Create model constructor

    public String getEntryID() {
        return mEntryID;
    }

    public void setEntryID(String mEntryID) {
        this.mEntryID = mEntryID;
    }

    public String getEntryAuthor() {
        return mEntryAuthor;
    }

    public void setEntryAuthor(String mEntryAuthor) {
        this.mEntryAuthor = mEntryAuthor;
    }

    public String getEntryTitle() {
        return mEntryTitle;
    }

    public void setEntryTitle(String mEntryTitle) {
        this.mEntryTitle = mEntryTitle;
    }

    public int getEntryVersion() {
        return mEntryVersion;
    }

    public void setEntryVersion(int mEntryVersion) {
        this.mEntryVersion = mEntryVersion;
    }

    public String getEntryNotes() {
        return mEntryNotes;
    }

    public void setEntryNotes(String mEntryNotes) {
        this.mEntryNotes = mEntryNotes;
    }

    public String getEntryObligations() {
        return mEntryObligations;
    }

    public void setEntryObligations(String mEntryObligations) {
        this.mEntryObligations = mEntryObligations;
    }

    public String getEntryDecisions() {
        return mEntryDecisions;
    }

    public void setEntryDecisions(String mEntryDecisions) {
        this.mEntryDecisions = mEntryDecisions;
    }

    public String getEntryOutcomes() {
        return mEntryOutcomes;
    }

    public void setEntryOutcomes(String mEntryOutcomes) {
        this.mEntryOutcomes = mEntryOutcomes;
    }
}
