/***
 *Project: mindFest (A trivia android application)
 * Designed and Coded by: Sohan Show (github.com/sohanshow)
 * Please create pull request to make changes to this file.
 * Languages used: Java, XML
 */
package com.sohansgithub.mindfest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class helpActivity<extras> extends AppCompatActivity {

   private static final String EXTRA_ANSWER_SHOWN = "contains if answer show value";

    private hintStore[] mHintBank = new hintStore[]{

            new hintStore(R.drawable.img1,
                    "Each line across and down contains five black dots and four white dots."),
            new hintStore(R.drawable.img2,
                    "Square 1 moves to bottom right, square 2 moves to top left, " +
                            "square 3 moves to top right and square 4 moves to bottom left. .\n"),
            new hintStore(R.drawable.img3,
                    "The arms move clockwise in turn 45°. ."),
            new hintStore(R.drawable.img4,
                    "The diamond is moving round each corner clockwise. " +
                            "The black portion alternates overlapping section/diamond/square."),
            new hintStore(R.drawable.img5,
                    "Each circle is repeated rotated.\n"),
    };



    private Button mShowHintButton;
    private TextView displayHint;






    public static boolean wasAnswerShown(Intent result){

        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        Intent intent = getIntent();
        int mCurrentIndex = intent.getIntExtra(HomeActivity.CURRENT_NUMBER,0);


        displayHint = (TextView) findViewById(R.id.displayHint);

        mShowHintButton = (Button) findViewById(R.id.showHintButton);
        mShowHintButton.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                displayHint.setText(mHintBank[mCurrentIndex].getHint());
                setAnswerShownResult(true);

                int cx = mShowHintButton.getWidth() /2 ;
                int cy = mShowHintButton.getHeight() / 2;
                float radius = mShowHintButton.getWidth();
                Animator anim = ViewAnimationUtils.createCircularReveal(mShowHintButton, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation){
                        super.onAnimationEnd(animation);
                        mShowHintButton.setVisibility(View.INVISIBLE);
                    }

                });
                anim.start();

            }
        });


    }
    private void setAnswerShownResult(boolean isAnswerShown){

        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }


}