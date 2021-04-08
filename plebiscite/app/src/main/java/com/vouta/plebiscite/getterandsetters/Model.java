package com.vouta.plebiscite.getterandsetters;

import android.os.Parcel;
import android.os.Parcelable;



public class Model implements Parcelable {

    String firstName;
    String Email;
    String RegNum;

    public Model(){


    }
    protected Model(Parcel in) {
        firstName = in.readString();
        Email = in.readString();
        RegNum = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getRegNum() {
        return RegNum;
    }

    public void setRegNum(String regNum) {
        RegNum = regNum;
    }

    public String getEmail() {
        return Email;
    }
    public String getFirstName() {
        return firstName;
    }


    public void setEmail(String email) {
        Email = email;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(Email);
        dest.writeString(RegNum);
    }
}
