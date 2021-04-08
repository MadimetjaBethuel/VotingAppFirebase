package com.vouta.plebliciteuserapp.voter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.vouta.plebliciteuserapp.organization.model.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class survey implements Parcelable {
    String Name;
    ArrayList<Question> Question;
    public survey(){}

    protected survey(Parcel in) {
        Name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<survey> CREATOR = new Creator<survey>() {
        @Override
        public survey createFromParcel(Parcel in) {
            return new survey(in);
        }

        @Override
        public survey[] newArray(int size) {
            return new survey[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<com.vouta.plebliciteuserapp.organization.model.Question> getQuestion() {
        return Question;
    }

    public void setQuestion(ArrayList<com.vouta.plebliciteuserapp.organization.model.Question> question) {
        Question = question;
    }
}
