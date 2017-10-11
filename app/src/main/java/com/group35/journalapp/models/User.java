package com.group35.journalapp.models;

import java.util.ArrayList;

/**
 * The User Model
 * <p>
 * Created by Joshua on 9/24/2017.
 */
public class User {
    private String mUserID;
    private String mEmail;
    private String mProfileImageLink;
    private ArrayList<Journal> mJournals;

    /**
     * Instantiates a new User. Firebase requires this so do not remove.
     */
    public User() {

    }

    /**
     * Instantiates a new User.
     *
     * @param userID      the username
     * @param email       the email
     * @param profileLink the profile link
     */
    public User(String userID, String email, String profileLink) {
        mUserID = userID;
        mEmail = email;
        mProfileImageLink = profileLink;
        mJournals = new ArrayList<Journal>();
    }


    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return mUserID;
    }

    /**
     * Sets username.
     *
     * @param mUsername the m username
     */
    public void setUsername(String mUsername) {
        this.mUserID = mUsername;
    }

    /**
     * Gets profile image link.
     *
     * @return the profile image link
     */
    public String getProfileImageLink() {
        return mProfileImageLink;
    }

    /**
     * Sets profile link.
     *
     * @param profileImageLink the profile image link
     */
    public void setProfileLink(String profileImageLink) {
        mProfileImageLink = profileImageLink;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        mEmail = email;
    }

    /**
     * Gets journals.
     *
     * @return the journals
     */
    public ArrayList<Journal> getJournals() {
        return mJournals;
    }

    /**
     * Sets journals.
     *
     * @param mJournals the journals
     */
    public void setJournals(ArrayList<Journal> mJournals) {
        this.mJournals = mJournals;
    }
}
