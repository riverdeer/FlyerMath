/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.tfbima.horse.flyermath2;

import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;
import java.util.Locale;


//every fuel check is a class, and the math at the end will compare the two class for the final maths


public class MainActivity extends AppCompatActivity {

    private EditText burnRateET;
    private EditText fuelNowET;
    private EditText fuelNowerET;
    private EditText timeNowET;
    private EditText timeNowerET;
    private EditText burnOutTimeET;
    private EditText VFRTimeET;
    private EditText IFRTimeET;

    public int checkDuration;

    private FuelState a = new FuelState();
    private FuelState b = new FuelState();
    private FuelState c = new FuelState();

    private static final String FuelOne = "FUEL_ONE";
    private static final String FuelTwo = "FUEL_TWO";
    private static final String TimeOne = "TIME_ONE";
    private static final String TimeTwo = "TIME_TWO";
    private static final String TimestampOne = "TIMESTAMP_ONE";
    private static final String TimestampTwo = "TIMESTAMP_TWO";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        burnOutTimeET = (EditText) findViewById(R.id.burnoutEdit);
        IFRTimeET = (EditText) findViewById(R.id.IFRTimeEdit);
        VFRTimeET = (EditText) findViewById(R.id.VFRTimeEdit);
        burnRateET = (EditText) findViewById(R.id.burnRateEdit);
        fuelNowET = (EditText) findViewById(R.id.init_gas);
        fuelNowerET = (EditText) findViewById(R.id.second_gas);
        timeNowET = (EditText) findViewById(R.id.init_time);
        timeNowerET = (EditText) findViewById(R.id.second_time);
        checkDuration = (int) 899999;

        //This will eventually be the saved instance state
        if (savedInstanceState == null) {
            // Just started
            a.setTimestamp(c.getTimestamp());
            b.setTimestamp(c.getTimestamp());

            a.setFuelPounds(c.getFuelPounds());
            b.setFuelPounds(c.getFuelPounds());

            a.setTimecheck(c.getTimecheck());
            b.setTimecheck(c.getTimecheck());
        } else {
            // App is being restored

            Calendar aCalendar = (Calendar) savedInstanceState.getSerializable(TimestampOne);
            Calendar bCalendar = (Calendar) savedInstanceState.getSerializable(TimestampTwo);

            a.setTimestamp(aCalendar);
            b.setTimestamp(bCalendar);
            String k = dateMaker(a.getTimestamp());
            timeNowET.setText(k);
            String l = dateMaker(b.getTimestamp());
            timeNowerET.setText(l);

            a.setFuelPounds(savedInstanceState.getLong(FuelOne));
            b.setFuelPounds(savedInstanceState.getLong(FuelTwo));
            fuelNowET.setText(String.valueOf(a.getFuelPounds()));
            fuelNowerET.setText(String.valueOf(b.getFuelPounds()));

            a.setTimecheck(savedInstanceState.getLong(TimeOne));
            b.setTimecheck(savedInstanceState.getLong(TimeTwo));

        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(TimestampOne, a.getTimestamp());
        outState.putSerializable(TimestampTwo, b.getTimestamp());

        outState.putDouble(FuelOne, a.getFuelPounds());
        outState.putDouble(FuelTwo, b.getFuelPounds());

        outState.putLong(TimeOne, a.getTimecheck());
        outState.putLong(TimeTwo, b.getTimecheck());

        super.onSaveInstanceState(outState);
    }

    public void stateNow1(View view) {

        //forms the fuel state for the first inputs

        try {
            a.setFuelPounds(Long.parseLong(fuelNowET.getText().toString()));
            Calendar now = Calendar.getInstance();
            long timen = now.getTimeInMillis();
            String k = dateMaker(now);
            timeNowET.setText(k);
            a.setTimestamp(now);
            a.setTimecheck(timen);

            ((Chronometer) findViewById(R.id.timer1)).setBase(SystemClock.elapsedRealtime());
            ((Chronometer) findViewById(R.id.timer1)).start();

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, getString(R.string.badFieldException), Toast.LENGTH_LONG).show();
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.timenow1).getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }

    public void stateNow2(View view) {

        //will form the fuel states for the second fields

        try {
            b.setFuelPounds(Long.parseLong(fuelNowerET.getText().toString()));
            Calendar now = Calendar.getInstance();
            long timen = now.getTimeInMillis();
            String k = dateMaker(now);
            timeNowerET.setText(k);
            b.setTimestamp(now);
            b.setTimecheck(timen);

            ((Chronometer) findViewById(R.id.timer1)).stop();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, getString(R.string.badFieldException), Toast.LENGTH_LONG).show();
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.timenow2).getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }


    private String dateMaker(Calendar x) {

        //takes the calender input and returns the string format HH:mm

        int nowhour = x.get(Calendar.HOUR_OF_DAY);
        int nowmin = x.get(Calendar.MINUTE);
        String nowHour = String.format(Locale.ENGLISH, "%02d", nowhour);
        String nowMin = String.format(Locale.ENGLISH, "%02d", nowmin);
        return nowHour + ":" + nowMin;
    }

    public void mathCalc1(View view) {

        //this will take the values set by the above and will calculate the needed data for the last
        //4 fields of the app

        try {
            double fuelBurn = a.getFuelPounds() - b.getFuelPounds();
            System.out.println(fuelBurn + " is total fuel burn as a double");

            long timeChangeLong = b.getTimecheck() - a.getTimecheck();
            System.out.println(timeChangeLong + " is the difference between the time stamps as long");
            int timeChangeInt = (int) timeChangeLong;
            int timeChangeInSecs = timeChangeInt / (int) 1000;
            System.out.println(timeChangeInSecs + " is int of timecheck math in secs");

            if (timeChangeInt > checkDuration) {

                //double timeChange = timeChangeInSecs / (double) 60000;
                //System.out.println(timeChange + " is double of timecheck math in min");

                float fuelpersec = (float) fuelBurn / timeChangeInSecs;
                System.out.println(fuelpersec + " is float of fuelburn by time change, or fuel per sec");
                double fuelperhour = (fuelpersec * 3600) +  0.5;
                int fuelperhourDisplay = (int) fuelperhour;
                    if (fuelperhourDisplay > 999) {
                    String burnhourly = Integer.toString(fuelperhourDisplay);
                    System.out.println(burnhourly + " is long of fuel burn lbs per hour");
                    burnRateET.setText(burnhourly);
                }
                else {
                    String burnhourly = Integer.toString(fuelperhourDisplay);
                    System.out.println(burnhourly + " is long of fuel burn lbs per hour");
                    String smallburnhourly = "0" + burnhourly;
                    burnRateET.setText(smallburnhourly);
                }

                Calendar grossTime = b.getTimestamp();
                /*long longgrossRemaining = (b.getFuelPounds() / fuelpersec);
                System.out.println(longgrossRemaining + " is long gross remaining");
                int grossRemaining = (int) longgrossRemaining;*/
                int grossRemaining = (int) (b.getFuelPounds() / fuelpersec);
                System.out.println(grossRemaining + " is gross remaining");
                int grossRemainingMin = grossRemaining / 60;
                System.out.println(grossRemainingMin + " is gross remaining min");
                grossTime.add(Calendar.MINUTE, grossRemainingMin);
                String gross = dateMaker(grossTime);
                burnOutTimeET.setText(gross);
                System.out.println(gross + " is gross time");

                grossTime.add(Calendar.MINUTE, -20);
                String vfr = dateMaker(grossTime);
                VFRTimeET.setText(vfr);

                grossTime.add(Calendar.MINUTE, -10);
                String ifr = dateMaker(grossTime);
                IFRTimeET.setText(ifr);
            } else {
                Toast.makeText(MainActivity.this, "WORDS", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, getString(R.string.badMathException), Toast.LENGTH_LONG).show();
        }
    }
    //TODO eliminate button connection.

    public void startTimer(View view) {

        //starts the timer. Also, resets the timer to blank to start it.

        ((Chronometer) findViewById(R.id.timer1)).setBase(SystemClock.elapsedRealtime());
        ((Chronometer) findViewById(R.id.timer1)).start();

    }

    public void stopTimer(View view) {

        //stops the timer (duh)

        ((Chronometer) findViewById(R.id.timer1)).stop();
    }

    public void clearAll(View view) {

        //this clears the text fields, and resets ALL test variables.

        ((Chronometer) findViewById(R.id.timer1)).stop();
        ((Chronometer) findViewById(R.id.timer1)).setBase(SystemClock.elapsedRealtime());

        a.setTimestamp(c.getTimestamp());
        b.setTimestamp(c.getTimestamp());

        a.setFuelPounds(c.getFuelPounds());
        b.setFuelPounds(c.getFuelPounds());

        a.setTimecheck(c.getTimecheck());
        b.setTimecheck(c.getTimecheck());

        burnOutTimeET.setText("");
        IFRTimeET.setText("");
        VFRTimeET.setText("");
        burnRateET.setText("");
        fuelNowET.setText("");
        fuelNowerET.setText("");
        timeNowET.setText("");
        timeNowerET.setText("");
        IFRTimeET.setText("");
        VFRTimeET.setText("");

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.tfbima.horse.flyermath2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.tfbima.horse.flyermath2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}