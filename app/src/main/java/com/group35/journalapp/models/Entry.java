package com.group35.journalapp.models;

import java.util.ArrayList;

/**
 * The Journal Entry Model
 * <p>
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
        //Set booleans to false as default
        mEntryDeleted = false;
        mEntryHidden = false;
        //Used to check if delete & hidden status under the form of "deleted_hidden"
        mEntryDeleted_Hidden = "false_false";
        mEntryContentList = new ArrayList<>();
    }

    /**
     * Gets entry id.
     *
     * @return the entry id
     */
    public String getEntryID() {
        return mEntryID;
    }

    /**
     * Sets entry id.
     *
     * @param mJournalEntryID the journal entry id
     */
    public void setEntryID(String mJournalEntryID) {
        mEntryID = mJournalEntryID;
    }

    /**
     * Gets entry author.
     *
     * @return the entry author
     */
    public String getEntryAuthor() {
        return mEntryAuthor;
    }

    /**
     * Sets entry author.
     *
     * @param mJournalEntryAuthor the journal entry author
     */
    public void setEntryAuthor(String mJournalEntryAuthor) {
        mEntryAuthor = mJournalEntryAuthor;
    }

    /**
     * Gets entry title.
     *
     * @return the entry title
     */
    public String getEntryTitle() {
        return mEntryTitle;
    }

    /**
     * Sets entry title.
     *
     * @param mJournalEntryTitle the journal entry title
     */
    public void setEntryTitle(String mJournalEntryTitle) {
        mEntryTitle = mJournalEntryTitle;
    }

    /**
     * Gets entry preview.
     *
     * @return the entry preview
     */
    public String getEntryPreview() {
        return mEntryPreview;
    }

    /**
     * Sets entry preview.
     *
     * @param mJournalEntryDescription the journal entry description
     */
    public void setEntryPreview(String mJournalEntryDescription) {
        mEntryPreview = mJournalEntryDescription;
    }

    /**
     * Gets last modify date.
     *
     * @return the last modify date
     */
    public String getLastModifyDate() {
        return mLastModifyDate;
    }

    /**
     * Sets last modify date.
     *
     * @param mJournalLastModifyDate the journal last modify date
     */
    public void setLastModifyDate(String mJournalLastModifyDate) {
        mLastModifyDate = mJournalLastModifyDate;
    }

    /**
     * Gets entry version.
     *
     * @return the entry version
     */
    public int getEntryVersion() {
        return mEntryVersion;
    }

    /**
     * Sets entry version.
     *
     * @param mJournalEntryVersion the journal entry version
     */
    public void setEntryVersion(int mJournalEntryVersion) {
        mEntryVersion = mJournalEntryVersion;
    }

    /**
     * Is journal entry hidden boolean.
     *
     * @return the boolean
     */
    public boolean isJournalEntryHidden() {
        return mEntryHidden;
    }

    /**
     * Sets journal entry hidden boolean.
     *
     * @param mJournalEntryHidden the journal entry hidden
     */
    public void setJournalEntryHidden(boolean mJournalEntryHidden) {
        mEntryHidden = mJournalEntryHidden;
    }

    /**
     * Is journal entry deleted boolean.
     *
     * @return the boolean
     */
    public boolean isJournalEntryDeleted() {
        return mEntryDeleted;
    }

    /**
     * Sets journal entry deleted.
     *
     * @param mJournalEntryDeleted the journal entry deleted boolean
     */
    public void setJournalEntryDeleted(boolean mJournalEntryDeleted) {
        mEntryDeleted = mJournalEntryDeleted;
    }

    /**
     * Gets entry content list.
     *
     * @return the entry content list
     */
    public ArrayList<String> getEntryContentList() {
        return mEntryContentList;
    }

    /**
     * Sets entry content list.
     *
     * @param mEntryContentList the entry content list
     */
    public void setEntryContentList(ArrayList<String> mEntryContentList) {
        this.mEntryContentList = mEntryContentList;
    }

    /**
     * Gets entry deleted hidden.
     *
     * @return the entry deleted hidden
     */
    public String getEntryDeleted_Hidden() {
        return mEntryDeleted_Hidden;
    }

    /**
     * Sets entry deleted_hidden.
     *
     * @param mEntryDeleted_Hidden the entry deleted_hidden STRING
     */
    public void setEntryDeleted_Hidden(String mEntryDeleted_Hidden) {
        this.mEntryDeleted_Hidden = mEntryDeleted_Hidden;
    }
}
