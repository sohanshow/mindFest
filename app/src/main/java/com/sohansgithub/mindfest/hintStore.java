package com.sohansgithub.mindfest;

public class hintStore {

    private int mImageId;
    private String mHint;

    public hintStore(int imageId, String hint){

        mImageId = imageId;
       mHint = hint;


    }


    public String getHint(){ return mHint; }


    public void setImageId(int imageId){ mImageId = imageId; }

    public void setHint(String answer){ mHint = answer;}

}
