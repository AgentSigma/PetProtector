package edu.orangecoastcollege.cs273.petprotector;

import android.net.Uri;

/**
 * Created by Daniel on 11/6/2017.
 * The Pet Object
 */

public class Pet {
    private int mID;
    private String mName;
    private String mDetails;
    private String mNumber;
    private Uri mImageUri;

    /**
     * Creates a new pet WITH a database Id
     * @param newID database ID
     * @param newName new pet name
     * @param newDetails new details for the pet
     * @param number new number for the pet
     * @param newImageUri new image uri of the pet
     */
    Pet(int newID, String newName, String newDetails, String number, Uri newImageUri){
        mID = newID;
        mName = newName;
        mDetails = newDetails;
        mImageUri = newImageUri;
        mNumber = number;
    }

    /**
     * Creates a new Pet object w/o an ID
     * @param newName new pet name
     * @param newDetails new details for the pet
     * @param number new number for the pet
     * @param newImageUri new image uri of the pet
     */
    Pet(String newName, String newDetails, String number, Uri newImageUri){
        mName = newName;
        mDetails = newDetails;
        mImageUri = newImageUri;
        mNumber = number;
    }

    /**
     * Gets the pet's name
     * @return pet's name
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the pet's name
     * @param name new name to be set to
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * Gets the pet's details
     * @return the pet's details
     */
    public String getDetails() {
        return mDetails;
    }

    /**
     * Sets the pet's details
     * @param details the new details to be set
     */
    public void setDetails(String details) {
        mDetails = details;
    }

    /**
     * Gets the pet's image uri
     * @return pet's image uri
     */
    public Uri getImageUri() {
        return mImageUri;
    }

    /**
     * Sets the pet's image uri
     * @param imageUri the pet's image uri
     */
    public void setImageUri(Uri imageUri) {
        mImageUri = imageUri;
    }

    /**
     * Gets the pet's database id
     * @return pet's database id
     */
    public int getID() {
        return mID;
    }

    /**
     * Gets the pet's owner's phone number
     * @return pet's owner's phone number
     */
    public String getNumber() {
        return mNumber;
    }

    /**
     * Sets the pet's owner's phone number
     * @param number pet's owner's phone number to be set
     */
    public void setNumber(String number) {
        mNumber = number;
    }

    /**
     * Creates a string describing the Pet object
     * @return string describing the Pet object
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
