package com.vouta.plebliciteuserapp.voter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class candidateModel implements Parcelable {

    private String NameProfile;
    private String EmailProfile;
    private String PhoneProfile;
    private String DescriptionProfile;
    private String CandidateID;
    private int NumberOfVotes;

    public candidateModel() {
    }

    protected candidateModel(Parcel in) {
        NameProfile = in.readString();
        EmailProfile = in.readString();
        PhoneProfile = in.readString();
        DescriptionProfile = in.readString();
        CandidateID = in.readString();
        NumberOfVotes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NameProfile);
        dest.writeString(EmailProfile);
        dest.writeString(PhoneProfile);
        dest.writeString(DescriptionProfile);
        dest.writeString(CandidateID);
        dest.writeInt(NumberOfVotes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<candidateModel> CREATOR = new Creator<candidateModel>() {
        @Override
        public candidateModel createFromParcel(Parcel in) {
            return new candidateModel(in);
        }

        @Override
        public candidateModel[] newArray(int size) {
            return new candidateModel[size];
        }
    };

    public String getNameProfile() {
        return NameProfile;
    }

    public void setNameProfile(String nameProfile) {
        NameProfile = nameProfile;
    }

    public String getEmailProfile() {
        return EmailProfile;
    }

    public void setEmailProfile(String emailProfile) {
        EmailProfile = emailProfile;
    }

    public String getPhoneProfile() {
        return PhoneProfile;
    }

    public void setPhoneProfile(String phoneProfile) {
        PhoneProfile = phoneProfile;
    }

    public String getDescriptionProfile() {
        return DescriptionProfile;
    }

    public void setDescriptionProfile(String descriptionProfile) {
        DescriptionProfile = descriptionProfile;
    }

    public String getCandidateID() {
        return CandidateID;
    }

    public void setCandidateID(String candidateID) {
        CandidateID = candidateID;
    }

    public int getNumberOfVotes() {
        return NumberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        NumberOfVotes = numberOfVotes;
    }
}