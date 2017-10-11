package com.group35.journalapp.models;

import java.util.ArrayList;

/**
 * The Journal Model
 * <p>
 * Created by Joshua on 9/27/2017.
 */
public class Journal {
    private String mJournalID;
    private String mJournalAuthor;
    private String mJournalName;
    private String mJournalDescription;
    private ArrayList<Entry> mJournalEntries;
    private String mJournalModifiedDate;
    private String mJournalImageLink;

    /**
     * Instantiates a new Journal.
     */
    public Journal() {

    }

    /**
     * Instantiates a new Journal.
     *
     * @param journalName         the journal name
     * @param journalDescription  the journal description
     * @param journalImage        the journal image
     * @param journalModifiedDate the journal modified date
     */
    public Journal(String journalName, String journalDescription, String journalImage, String journalModifiedDate) {
        mJournalName = journalName;
        mJournalDescription = journalDescription;
        mJournalImageLink = journalImage;
        mJournalEntries = new ArrayList<>();
        mJournalModifiedDate = journalModifiedDate;
    }

    /**
     * Gets journal id.
     *
     * @return the journal id
     */
    public String getJournalID() {
        return mJournalID;
    }

    /**
     * Sets journal id.
     *
     * @param journalID the journal id
     */
    public void setJournalID(String journalID) {
        mJournalID = journalID;
    }

    /**
     * Gets journal author.
     *
     * @return the journal author
     */
    public String getJournalAuthor() {
        return mJournalAuthor;
    }

    /**
     * Sets journal author.
     *
     * @param journalAuthor the journal author
     */
    public void setJournalAuthor(String journalAuthor) {
        mJournalAuthor = journalAuthor;
    }

    /**
     * Gets journal name.
     *
     * @return the journal name
     */
    public String getJournalName() {
        return mJournalName;
    }

    /**
     * Sets journal name.
     *
     * @param journalName the journal name
     */
    public void setJournalName(String journalName) {
        mJournalName = journalName;
    }

    /**
     * Gets journal entries.
     *
     * @return the journal entries
     */
    public ArrayList<Entry> getJournalEntries() {
        return mJournalEntries;
    }

    /**
     * Sets journal entries.
     *
     * @param journalEntries the journal entries
     */
    public void setJournalEntries(ArrayList<Entry> journalEntries) {
        mJournalEntries = journalEntries;
    }

    /**
     * Gets journal description.
     *
     * @return the journal description
     */
    public String getJournalDescription() {
        return mJournalDescription;
    }

    /**
     * Sets journal description.
     *
     * @param journalDescription the journal description
     */
    public void setJournalDescription(String journalDescription) {
        mJournalDescription = journalDescription;
    }

    /**
     * Gets journal modified date.
     *
     * @return the journal modified date
     */
    public String getJournalModifiedDate() {
        return mJournalModifiedDate;
    }

    /**
     * Sets journal modified date.
     *
     * @param mJournalDate the m journal date
     */
    public void setJournalModifiedDate(String mJournalDate) {
        mJournalModifiedDate = mJournalDate;
    }

    /**
     * Gets journal image link.
     *
     * @return the journal image link
     */
    public String getJournalImageLink() {
        return mJournalImageLink;
    }

    /**
     * Sets journal image link.
     *
     * @param mJournalImageLink the journal image link
     */
    public void setJournalImageLink(String mJournalImageLink) {
        this.mJournalImageLink = mJournalImageLink;
    }
}
