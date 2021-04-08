package com.vouta.plebliciteuserapp.organization.model;

import android.os.Parcel;
import android.os.Parcelable;

public class modelregister implements Parcelable {

    String RegNum;
    public modelregister(){}

    protected modelregister(Parcel in) {
        RegNum = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RegNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<modelregister> CREATOR = new Creator<modelregister>() {
        @Override
        public modelregister createFromParcel(Parcel in) {
            return new modelregister(in);
        }

        @Override
        public modelregister[] newArray(int size) {
            return new modelregister[size];
        }
    };

    public String getRegNum() {
        return RegNum;
    }

    public void setRegNum(String regNum) {
        RegNum = regNum;
    }
}


