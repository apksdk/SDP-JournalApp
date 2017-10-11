package com.group35.journalapp.models;

/**
 * The Journal Entry Content Model
 * <p>
 * Created by Josh on 28/09/2017.
 */
public class EntryContent {
    private String mEntryID;
    private String mEntryNotes;
    private String mEntryObligations;
    private String mEntryDecisions;
    private String mEntryOutcomes;
    private int mEntryVersion;
    private String mEntryModifiedDate;

    /**
     * Instantiates a new Entry content. For Firebase do not delete.
     */
    public EntryContent() {

    }

    /**
     * Instantiates a new Entry content.
     *
     * @param entryNotes       the entry notes
     * @param entryObligations the entry obligations
     * @param entryDecisions   the entry decisions
     * @param entryOutcomes    the entry outcomes
     * @param date             the entry date
     * @param entryVersion     the entry version
     */
    public EntryContent(String entryNotes, String entryObligations, String entryDecisions, String entryOutcomes, String date, int entryVersion) {
        mEntryNotes = entryNotes;
        mEntryObligations = entryObligations;
        mEntryDecisions = entryDecisions;
        mEntryOutcomes = entryOutcomes;
        mEntryModifiedDate = date;
        mEntryVersion = entryVersion;
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
     * @param mEntryID the entry id
     */
    public void setEntryID(String mEntryID) {
        this.mEntryID = mEntryID;
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
     * @param mEntryVersion the entry version
     */
    public void setEntryVersion(int mEntryVersion) {
        this.mEntryVersion = mEntryVersion;
    }

    /**
     * Gets entry notes.
     *
     * @return the entry notes
     */
    public String getEntryNotes() {
        return mEntryNotes;
    }

    /**
     * Sets entry notes.
     *
     * @param mEntryNotes the entry notes
     */
    public void setEntryNotes(String mEntryNotes) {
        this.mEntryNotes = mEntryNotes;
    }

    /**
     * Gets entry obligations.
     *
     * @return the entry obligations
     */
    public String getEntryObligations() {
        return mEntryObligations;
    }

    /**
     * Sets entry obligations.
     *
     * @param mEntryObligations the entry obligations
     */
    public void setEntryObligations(String mEntryObligations) {
        this.mEntryObligations = mEntryObligations;
    }

    /**
     * Gets entry decisions.
     *
     * @return the entry decisions
     */
    public String getEntryDecisions() {
        return mEntryDecisions;
    }

    /**
     * Sets entry decisions.
     *
     * @param mEntryDecisions the entry decisions
     */
    public void setEntryDecisions(String mEntryDecisions) {
        this.mEntryDecisions = mEntryDecisions;
    }

    /**
     * Gets entry outcomes.
     *
     * @return the entry outcomes
     */
    public String getEntryOutcomes() {
        return mEntryOutcomes;
    }

    /**
     * Sets entry outcomes.
     *
     * @param mEntryOutcomes the entry outcomes
     */
    public void setEntryOutcomes(String mEntryOutcomes) {
        this.mEntryOutcomes = mEntryOutcomes;
    }

    /**
     * Gets entry modified date.
     *
     * @return the entry modified date
     */
    public String getEntryModifiedDate() {
        return mEntryModifiedDate;
    }

    /**
     * Sets entry modified date.
     *
     * @param mEntryModifiedDate the entry modified date
     */
    public void setEntryModifiedDate(String mEntryModifiedDate) {
        this.mEntryModifiedDate = mEntryModifiedDate;
    }
}
