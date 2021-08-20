package com.sohansgithub.mindfest;

public class Questions {

    private int mImageId;
    private String mAnswer;

    public Questions(int imageId, String answer){

        mImageId = imageId;
        mAnswer = answer;


    }


    public int getImageId(){ return mImageId; }

    public String getAnswer(){ return mAnswer; }

    public void setImageId(int imageId){ mImageId = imageId; }

    public void setAnswer(String answer){ mAnswer = answer;}




}
