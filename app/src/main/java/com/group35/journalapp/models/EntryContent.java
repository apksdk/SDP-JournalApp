package com.group35.journalapp.models;

/**
 * Created by Josh on 28/09/2017.
 */
public class EntryContent {
    private String mEntryID;
    private String mEntryAuthor;
    private String mEntryTitle;
    private String mEntryNotes;
    private String mEntryObligations;
    private String mEntryDecisions;
    private String mEntryOutcomes;
    private int mEntryVersion;
    private String mEntryModifiedDate;

    /**
     * Instantiates a new Entry content.
     *
     * @param entryTitle       the entry title
     * @param entryNotes       the entry notes
     * @param entryObligations the entry obligations
     * @param entryDecisions   the entry decisions
     * @param entryOutcomes    the entry outcomes
     */
    public EntryContent(String entryTitle, String entryNotes, String entryObligations, String entryDecisions, String entryOutcomes) {
        mEntryTitle = entryTitle;
        mEntryNotes = entryNotes;
        mEntryObligations = entryObligations;
        mEntryDecisions = entryDecisions;
        mEntryOutcomes = entryOutcomes;
        mEntryModifiedDate = "04/12/1991";
        //Any significance of that date?

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
     * @param mEntryID the m entry id
     */
    public void setEntryID(String mEntryID) {
        this.mEntryID = mEntryID;
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
     * @param mEntryAuthor the m entry author
     */
    public void setEntryAuthor(String mEntryAuthor) {
        this.mEntryAuthor = mEntryAuthor;
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
     * @param mEntryTitle the m entry title
     */
    public void setEntryTitle(String mEntryTitle) {
        this.mEntryTitle = mEntryTitle;
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
     * @param mEntryVersion the m entry version
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
     * @param mEntryNotes the m entry notes
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
     * @param mEntryObligations the m entry obligations
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
     * @param mEntryDecisions the m entry decisions
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
     * @param mEntryOutcomes the m entry outcomes
     */
    public void setEntryOutcomes(String mEntryOutcomes) {
        this.mEntryOutcomes = mEntryOutcomes;
    }
}
