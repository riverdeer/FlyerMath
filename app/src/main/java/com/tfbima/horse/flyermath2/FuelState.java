package com.tfbima.horse.flyermath2;

import java.util.Calendar;

/**
 * Created by horse on 7/19/16. This creates the template for the Fuel state object. Every
 * Fuel State is a snapshot of the fuel and time at the moment is it checked. This is useful, as it
 * allows us to store this data gathered from the various methods in the main activity.
 */
class FuelState {
//declared the variables required for fuel states
        private int fuelPounds;
        private long timecheck;
        private Calendar timestamp;
//getters and setters for each variable
        void setFuelPounds (int f){
            fuelPounds = f;
        }
        int getFuelPounds(){
            return fuelPounds;
        }

        void setTimecheck (long t){
            timecheck = t;
        }
        long getTimecheck(){
            return timecheck;
        }

        void setTimestamp(Calendar d){
            timestamp = d;
        }
        Calendar getTimestamp(){
            return timestamp;
        }

}
