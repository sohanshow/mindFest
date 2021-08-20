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
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       getSupportActionBar().hide();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent home = new Intent(MainActivity.this, HomeActivity.class);

                startActivity(home); //starts the home activity of the appllication.

               finish(); // finishes the splashscreen
           }
       }, SPLASH_SCREEN_TIME_OUT);
    }
}