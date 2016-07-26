package com.adurcup.pestcontrolsellerapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kshivang on 21/07/16.
 * This java class is just to hold variable which parcel
 * when new order is received
 */

public class Commitment implements Parcelable{
    private String date, time, area, location, addressLine1, addressLine2;

    public Commitment(String date, String time, String area, String location,
                      String addressLine1, String addressLine2){
        this.date = date;
        this.time = time;
        this.area = area;
        this.location = location;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getArea() {
        return area;
    }

    public String getLocation() {
        return location;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public Commitment( Parcel in ){
        String[] data = new String[6];

        in.readStringArray(data);
        this.date = data[0];
        this.time = data[1];
        this.area = data[2];
        this.location = data[3];
        this.addressLine1 = data[4];
        this.addressLine2 = data[5];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.date, this.time, this.area,
                this.location, this.addressLine1, this.addressLine2
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Commitment createFromParcel(Parcel in) {
            return new Commitment(in);
        }

        public Commitment[] newArray(int size) {
            return new Commitment[size];
        }
    };
}
