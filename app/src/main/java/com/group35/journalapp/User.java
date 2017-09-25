package com.group35.journalapp;

/**
 * Created by Joshua on 9/24/2017.
 */
public class User {
    private String mUsername;
    private String mEmail;
    private String mProfileImageLink;

    /**
     * Instantiates a new User. Firebase requires this so do not remove.
     */
    public User() {

    }

    /**
     * Instantiates a new User.
     *
     * @param username    the username
     * @param email       the email
     * @param profileLink the profile link
     */
    public User(String username, String email ,String profileLink) {
        mUsername = username;
        mEmail = email;
        mProfileImageLink = profileLink;
    }


    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * Sets username.
     *
     * @param mUsername the m username
     */
    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
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
}
