package com.group35.journalapp.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joshua on 9/27/2017.
 */
public class JournalEntry {
    private String mJournalEntryTitle;
    private String mJournalEntryDescription;
    private Date mJournalLastModifyDate;
    private int mJournalEntryVersion;
    private boolean mJournalEntryHidden;
    private boolean mJournalEntryDeleted;
    private ArrayList<EntryContent> mEntryContentList;

    /**
     * Instantiates a new Journal entry. Firebase needs this, so do not delete.
     */
    public JournalEntry() {

    }

    /**
     * Instantiates a new Journal entry.
     *
     * @param entryTitle       the entry title
     * @param entryDescription the entry description
     * @param lastModifyDate   the last modify date
     * @param entryVersion     the entry version
     */
    public JournalEntry(String entryTitle, String entryDescription, Date lastModifyDate, int entryVersion) {
        this.mJournalEntryTitle = entryTitle;
        this.mJournalEntryDescription = entryDescription;
        this.mJournalLastModifyDate = lastModifyDate;
        this.mJournalEntryVersion = entryVersion;
        mJournalEntryDeleted = false;
        mJournalEntryDeleted = false;
        mEntryContentList = new ArrayList<EntryContent>();
    }

    public String getJournalEntryTitle() {
        return mJournalEntryTitle;
    }

    public void setJournalEntryTitle(String mJournalEntryTitle) {
        this.mJournalEntryTitle = mJournalEntryTitle;
    }

    public String getJournalEntryDescription() {
        return mJournalEntryDescription;
    }

    public void setJournalEntryDescription(String mJournalEntryDescription) {
        this.mJournalEntryDescription = mJournalEntryDescription;
    }

    public Date getJournalLastModifyDate() {
        return mJournalLastModifyDate;
    }

    public void setJournalLastModifyDate(Date mJournalLastModifyDate) {
        this.mJournalLastModifyDate = mJournalLastModifyDate;
    }

    public int getJournalEntryVersion() {
        return mJournalEntryVersion;
    }

    public void setJournalEntryVersion(int mJournalEntryVersion) {
        this.mJournalEntryVersion = mJournalEntryVersion;
    }

    public boolean isJournalEntryHidden() {
        return mJournalEntryHidden;
    }

    public void setJournalEntryHidden(boolean mJournalEntryHidden) {
        this.mJournalEntryHidden = mJournalEntryHidden;
    }

    public boolean isJournalEntryDeleted() {
        return mJournalEntryDeleted;
    }

    public void setJournalEntryDeleted(boolean mJournalEntryDeleted) {
        this.mJournalEntryDeleted = mJournalEntryDeleted;
    }

    public ArrayList<EntryContent> getEntryContentList() {
        return mEntryContentList;
    }

    public void setEntryContentList(ArrayList<EntryContent> mEntryContentList) {
        this.mEntryContentList = mEntryContentList;
    }
}
