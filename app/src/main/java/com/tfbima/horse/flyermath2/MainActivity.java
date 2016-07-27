/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.tfbima.horse.flyermath2;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
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

    private FuelState a = new FuelState();
    private FuelState b = new FuelState();
    private FuelState c = new FuelState();

    private static final String FuelOne = "FUEL_ONE";
    private static final String FuelTwo = "FUEL_TWO";
    private static final String TimeOne = "TIME_ONE";
    private static final String TimeTwo = "TIME_TWO";
    private static final String TimestampOne = "TIMESTAMP_ONE";
    private static final String TimestampTwo = "TIMESTAMP_TWO";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//This will eventually be the saved instance state
        if(savedInstanceState == null){
            // Just started
            a.setTimestamp(c.getTimestamp());
            b.setTimestamp(c.getTimestamp());

            a.setFuelPounds(c.getFuelPounds());
            b.setFuelPounds(c.getFuelPounds());

            a.setTimecheck(c.getTimecheck());
            b.setTimecheck(c.getTimecheck());
        } else {
            // App is being restored

           // a.setTimestamp();
           // b.setTimestamp(savedInstanceState.getSerializable(TimestampTwo));

            Calendar bCalendar = (Calendar) savedInstanceState.getSerializable(TimestampTwo);
            Calendar aCalendar = (Calendar) savedInstanceState.getSerializable(TimestampOne);

            a.setTimestamp(aCalendar);
            b.setTimestamp(bCalendar);

            a.setFuelPounds(savedInstanceState.getInt(FuelOne));
            b.setFuelPounds(savedInstanceState.getInt(FuelTwo));

            a.setTimecheck(savedInstanceState.getLong(TimeOne));
            b.setTimecheck(savedInstanceState.getLong(TimeTwo));

        }

        //

        burnOutTimeET = (EditText) findViewById(R.id.burnoutEdit);
        IFRTimeET = (EditText) findViewById(R.id.IFRTimeEdit);
        VFRTimeET = (EditText) findViewById(R.id.VFRTimeEdit);
        burnRateET = (EditText) findViewById(R.id.burnRateEdit);
        fuelNowET = (EditText) findViewById(R.id.init_gas);
        fuelNowerET = (EditText) findViewById(R.id.second_gas);
        timeNowET = (EditText) findViewById(R.id.init_time);
        timeNowerET = (EditText) findViewById(R.id.second_time);

    }

    public void stateNow1(View view) {

        //forms the fuel state for the first inputs

        try {
            a.setFuelPounds(Integer.parseInt(fuelNowET.getText().toString()));
            Calendar now = Calendar.getInstance();
            long timen = now.getTimeInMillis();
            String k = dateMaker(now);
            timeNowET.setText(k);
            a.setTimestamp(now);
            a.setTimecheck(timen);

            ((Chronometer) findViewById(R.id.timer1)).setBase(SystemClock.elapsedRealtime());
            ((Chronometer) findViewById(R.id.timer1)).start();

        }
        catch (Exception e){
            Toast.makeText(MainActivity.this, getString(R.string.badFieldException), Toast.LENGTH_LONG).show();
        }
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.timenow1).getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }

    public void stateNow2(View view) {

        //will form the fuel states for the second fields

            try {
                b.setFuelPounds(Integer.parseInt(fuelNowerET.getText().toString()));
                Calendar now = Calendar.getInstance();
                long timen = now.getTimeInMillis();
                String k = dateMaker(now);
                timeNowerET.setText(k);
                b.setTimestamp(now);
                b.setTimecheck(timen);

                ((Chronometer) findViewById(R.id.timer1)).stop();
            }
            catch (Exception e) {
                Toast.makeText(MainActivity.this, getString(R.string.badFieldException), Toast.LENGTH_LONG).show();
            }
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.timenow2).getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }

    //takes the calender input and returns the string format HH:mm

    private String dateMaker(Calendar x){
        int nowhour = x.get(Calendar.HOUR_OF_DAY);
        int nowmin = x.get(Calendar.MINUTE);
        String nowHour = String.format(Locale.ENGLISH, "%02d", nowhour);
        String nowMin = String.format(Locale.ENGLISH, "%02d", nowmin);
        return nowHour + ":" + nowMin;
    }

    ///this will take the values set by the above and will calculate the needed data for the last 4 fields of the app

    public void mathCalc1(View view){
        try {
            int fuelBurn = a.getFuelPounds() - b.getFuelPounds();
            System.out.println(fuelBurn + " is total fuel burn int");

            long timeChangeLong = b.getTimecheck() - a.getTimecheck();
            System.out.println(timeChangeLong + " is the difference between the time stamps");
            int timeChangeInMillis = (int) timeChangeLong;
            System.out.println(timeChangeInMillis + " is int of timecheck math in millis");

            int timeChange = timeChangeInMillis / 60000;
            System.out.println(timeChange + " is int of timecheck math in min");

            int fuelpermin = fuelBurn / timeChange;
            System.out.println(fuelpermin + " is int of fuelburn by time change, or fuel per min");
            int fuelperhour = fuelpermin * 60;
            String burnhourly = Long.toString(fuelperhour);
            System.out.println(burnhourly + " is int of fuel burn lbs per hour");
            burnRateET.setText(burnhourly);

            Calendar grossTime = b.getTimestamp();
            int grossRemaining = (b.getFuelPounds() / fuelpermin);
            System.out.println(grossRemaining + " is gross remaining");
            grossTime.add(Calendar.MINUTE, grossRemaining);
            String gross = dateMaker(grossTime);
            burnOutTimeET.setText(gross);
            System.out.println(gross + " is gross time");

            grossTime.add(Calendar.MINUTE, -20);
            String vfr = dateMaker(grossTime);
            VFRTimeET.setText(vfr);

            grossTime.add(Calendar.MINUTE, -10);
            String ifr = dateMaker(grossTime);
            IFRTimeET.setText(ifr);

        }
        catch (Exception e){
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

    public void clearAll ( View view) {

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

    protected void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);

        outState.putSerializable(TimestampOne, a.getTimestamp());
        outState.putSerializable(TimestampTwo, a.getTimestamp());

        outState.putInt(FuelOne, a.getFuelPounds());
        outState.putInt(FuelTwo, b.getFuelPounds());

        outState.putLong(TimeOne, a.getTimecheck());
        outState.putLong(TimeTwo, b.getTimecheck());

    }
}