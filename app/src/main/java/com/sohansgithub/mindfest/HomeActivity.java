/***
 *Project: mindFest (A trivia android application)
 * Designed and Coded by: Sohan Show (github.com/sohanshow)
 * Please create pull request to make changes to this file.
 * Languages used: Java, XML
 */
package com.sohansgithub.mindfest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private ImageSwitcher mQuestionImage;
    private Button mbuttonA;
    private Button mbuttonB;
    private Button mbuttonC;
    private Button mbuttonD;
    private Button mResetButton;
    private ImageButton mprevButton;
    private ImageButton mnextButton;
    private ImageButton mHintButton;
    private ImageButton helpButton;
    private TextView mScoreCounter;
    private boolean mHasHint;
    private boolean prevActivated;

    private static final int REQUEST_CODE_CHEAT = 0;

    public static final String CURRENT_NUMBER = "com.sohansgithub.mindset";

    //creating a TAG for log file
    private static final String TAG ="QuizActivity";
    //---------- LOG creating finished-----------

    //Adding key for the value

    private static final String KEY_INDEX = "index";



    //---------------Setting up the Question bank for the Display Switcher
    private Questions[] mQuestionBank = new Questions[]{

            new Questions(R.drawable.img1, "A"),
            new Questions(R.drawable.img2, "A"),
            new Questions(R.drawable.img3, "C"),
            new Questions(R.drawable.img4, "D"),
            new Questions(R.drawable.img5, "C"),
    };
//--------------Question Bank set-up done successfully!---------------------

    private int mCurrentIndex = 0; //stores index for the current question
    private int mPreviousIndex = 0;// stores index for the previous question
    int scoreCounter = 0;//stores the score of the player
    int counter = 0;//stores the number of questions done.
    boolean finished = false;
    private boolean clicked_button = false;
    int tracker = mQuestionBank.length; //stores the total number of questions.
    private int endCounter = 0;
    private int hintCounter = 0;
    private int hintCap = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//---------Setting up the display of the questions-----------------------------
        mScoreCounter = (TextView) findViewById(R.id.score_counter); // for the score display
        mQuestionImage = (ImageSwitcher) findViewById(R.id.displayQuestion);
        mQuestionImage.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        updateQuestion();


//------------------------------setting up display done successfully-----------------------------

        //----------the next Button----------------------
        mnextButton = (ImageButton) findViewById(R.id.nextButton);
        mnextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!finished) {


                    if (!clicked_button && !prevActivated) {

                        Toast clicked_toast = Toast.makeText(HomeActivity.this,
                                "Please Select an Answer", Toast.LENGTH_SHORT);
                        clicked_toast.show();
                    } else {
                        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                        updateQuestion();
                        mbuttonA.setEnabled(true);
                        mbuttonB.setEnabled(true);
                        mbuttonC.setEnabled(true);
                        mbuttonD.setEnabled(true);
                        clicked_button = false;
                        mHasHint = false;
                        prevActivated = false;
                    }

                } else {

                    if (endCounter < 1) {
                        if (scoreCounter < 3) {
                            Toast toast1 = Toast.makeText(HomeActivity.this, "You performed below average :(",
                                    Toast.LENGTH_SHORT);
                            toast1.setGravity(Gravity.TOP, 0, 0);
                            toast1.show();
                            endCounter++;
                        } else if (scoreCounter > 3) {

                            Toast toast2 = Toast.makeText(HomeActivity.this,
                                    "Nice! You are way above the average.", Toast.LENGTH_SHORT);

                            toast2.setGravity(Gravity.TOP, 0, 0);
                            toast2.show();
                            endCounter++;
                        } else if (scoreCounter > 4 || scoreCounter == 5) {
                            Toast toast3 = Toast.makeText(HomeActivity.this,
                                    "You are a genius!", Toast.LENGTH_SHORT);

                            toast3.setGravity(Gravity.TOP, 0, 0);
                            toast3.show();
                            endCounter++;
                        }
                    } else {
                        Toast toast = Toast.makeText(HomeActivity.this,
                                "All Questions over. Please RESET", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    }
                }

            }
        });//------------------End of NEXT button


        //--------Configuring the previous Button

        mprevButton = (ImageButton) findViewById(R.id.prevButton);
        mprevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == 0) {
                    // This is the base case.
                    mPreviousIndex = mCurrentIndex;
                } else {
                    mPreviousIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    mCurrentIndex = mPreviousIndex;
                    //setting all buttons false so that user can't repeat answer
                    mbuttonA.setEnabled(false);
                    mbuttonB.setEnabled(false);
                    mbuttonC.setEnabled(false);
                    mbuttonD.setEnabled(false);//-----------------
                    prevActivated = true;
                }
                updateQuestion();
            }
        }); //-----------Previous Image Button successfully configured

        //-----------Configuring the RESET button----------------------------------
        mResetButton = (Button) findViewById(R.id.resetButton);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finished = false;
                mCurrentIndex = 0;
                updateQuestion();
                scoreCounter = 0;
                endCounter = 0;
                counter = 0;
                updateScore();
                hintCounter=0;
                mbuttonA.setEnabled(true);
                mbuttonB.setEnabled(true);
                mbuttonC.setEnabled(true);
                mbuttonD.setEnabled(true);

            }
        });//--------------RESET button configured successfully--------------


        //----------------Starting to initialize all the option buttons A through D---------------

        mbuttonA = (Button) findViewById(R.id.buttonA);
        mbuttonA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkAnswer("A");

                clicked_button = true;
            }

        });

        mbuttonB = (Button) findViewById(R.id.buttonB);
        mbuttonB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkAnswer("B");

                clicked_button = true;
            }

        });

        mbuttonC = (Button) findViewById(R.id.buttonC);
        mbuttonC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkAnswer("C");

                clicked_button = true;
            }

        });

        mbuttonD = (Button) findViewById(R.id.buttonD);
        mbuttonD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkAnswer("A");

                clicked_button = true;
            }

        });

        //----------------- End of options buttons initialization----------------------


        //----Configuring the HINT button-----------
        mHintButton = (ImageButton) findViewById(R.id.hintButton);
        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hintCounter >= hintCap)
                    mHintButton.setEnabled(false);

                else{
                    openHelpActivity();
                    int remaining = hintCap - hintCounter;
                    String message = "You have " + remaining + " attempts left." ;
                    Toast tst = Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT);
                    tst.show();
                    hintCounter++;

                }

            }
        });



    }


    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){

        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){

        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop(){

        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

    }

    public void openHelpActivity(){

        Intent intent = new Intent(this, helpActivity.class);
        intent.putExtra(CURRENT_NUMBER,mCurrentIndex);
        startActivityForResult(intent,0);
    }



    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHEAT ) {

                if (data != null) {
                    mHasHint = helpActivity.wasAnswerShown(data);
                }

            }
        }





    private void updateQuestionPrev() {
        int question = mQuestionBank[mPreviousIndex].getImageId();
        mQuestionImage.setImageResource(question);

    }

    private void updateQuestion() {

        int question = mQuestionBank[mCurrentIndex].getImageId();
        mQuestionImage.setImageResource(question);

    }

    private void updateScore() {

        mScoreCounter.setText("Your Score: " + scoreCounter);
    }



    private void checkAnswer(String userPressed) {
        //First storing the correct answer to compare later
        String correctAnswer = mQuestionBank[mCurrentIndex].getAnswer();

        int messageId = 0;

        counter++;

        if (mHasHint) {
            messageId = R.string.judgementToast;
            Toast hintUsedMessage = Toast.makeText(this, messageId, Toast.LENGTH_SHORT);
            hintUsedMessage.show();
        } else {

            if (userPressed.equalsIgnoreCase(correctAnswer)) {

                messageId = R.string.correct_toast;
                scoreCounter++;
                updateScore();
            } else
                messageId = R.string.incorrect_toast;

            //--Now disabling all the buttons because answer has been registered
            mbuttonA.setEnabled(false);
            mbuttonB.setEnabled(false);
            mbuttonC.setEnabled(false);
            mbuttonD.setEnabled(false); //----------------------


            Toast toast = Toast.makeText(this, messageId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);

            toast.show();


            if (counter == tracker)  //condition for no more questions
                finished = true;



        }

    }

}