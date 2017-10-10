package com.group35.journalapp.models;

import java.util.ArrayList;

/**
 * Created by Joshua on 9/27/2017.
 */
public class Entry {
    private String mEntryID;
    private String mEntryAuthor;
    private String mEntryTitle;
    private String mEntryPreview;
    private String mLastModifyDate;
    private int mEntryVersion;
    private boolean mEntryHidden;
    private boolean mEntryDeleted;
    private String mEntryDeleted_Hidden;
    private ArrayList<String> mEntryContentList;

    /**
     * Instantiates a new Journal entry. Firebase needs this, so do not delete.
     */
    public Entry() {

    }

    /**
     * \
     * Instantiates a new Journal entry.
     *
     * @param entryTitle     the entry title
     * @param entryPreview   the entry preview
     * @param lastModifyDate the last modify date
     * @param entryVersion   the entry version
     */
    public Entry(String entryTitle, String entryPreview, String lastModifyDate, int entryVersion) {
        mEntryTitle = entryTitle;
        mEntryPreview = entryPreview;
        mLastModifyDate = lastModifyDate;
        mEntryVersion = entryVersion;
        mEntryDeleted = false;
        mEntryHidden = false;
        mEntryDeleted_Hidden = "false_false";
        mEntryContentList = new ArrayList<>();
    }

    public String getEntryID() {
        return mEntryID;
    }

    public void setEntryID(String mJournalEntryID) {
        mEntryID = mJournalEntryID;
    }

    public String getEntryAuthor() {
        return mEntryAuthor;
    }

    public void setEntryAuthor(String mJournalEntryAuthor) {
        mEntryAuthor = mJournalEntryAuthor;
    }

    //EntryID is different to JournalID as entryID is used to find previous versions of the same entry.
    //Author is passed from JournalAuthor.

    public String getEntryTitle() {
        return mEntryTitle;
    }

    public void setEntryTitle(String mJournalEntryTitle) {
        mEntryTitle = mJournalEntryTitle;
    }

    public String getEntryPreview() {
        return mEntryPreview;
    }

    public void setEntryPreview(String mJournalEntryDescription) {
        mEntryPreview = mJournalEntryDescription;
    }

    public String getLastModifyDate() {
        return mLastModifyDate;
    }

    public void setLastModifyDate(String mJournalLastModifyDate) {
        mLastModifyDate = mJournalLastModifyDate;
    }

    public int getEntryVersion() {
        return mEntryVersion;
    }

    public void setEntryVersion(int mJournalEntryVersion) {
        mEntryVersion = mJournalEntryVersion;
    }

    public boolean isJournalEntryHidden() {
        return mEntryHidden;
    }

    public void setJournalEntryHidden(boolean mJournalEntryHidden) {
        mEntryHidden = mJournalEntryHidden;
    }

    public boolean isJournalEntryDeleted() {
        return mEntryDeleted;
    }

    public void setJournalEntryDeleted(boolean mJournalEntryDeleted) {
        mEntryDeleted = mJournalEntryDeleted;
    }

    public ArrayList<String> getEntryContentList() {
        return mEntryContentList;
    }

    public void setEntryContentList(ArrayList<String> mEntryContentList) {
        this.mEntryContentList = mEntryContentList;
    }

    public String getEntryDeleted_Hidden() {
        return mEntryDeleted_Hidden;
    }

    public void setEntryDeleted_Hidden(String mEntryDeleted_Hidden) {
        this.mEntryDeleted_Hidden = mEntryDeleted_Hidden;
    }
}
