package com.vouta.plebliciteuserapp.voter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Voters implements Parcelable {

    private String VoterId;
    private String VoterName;
    private String VoterSurname;
    private String VoterEmail;
    private String VoterPhone;
    private int VotingState;

    public Voters() {
    }


    protected Voters(Parcel in) {
        VoterId = in.readString();
        VoterName = in.readString();
        VoterSurname = in.readString();
        VoterEmail = in.readString();
        VoterPhone = in.readString();
        VotingState = in.readInt();
    }

    public static final Creator<Voters> CREATOR = new Creator<Voters>() {
        @Override
        public Voters createFromParcel(Parcel in) {
            return new Voters(in);
        }

        @Override
        public Voters[] newArray(int size) {
            return new Voters[size];
        }
    };

    public String getVoterId() {
        return VoterId;
    }

    public void setVoterId(String voterId) {
        VoterId = voterId;
    }

    public String getVoterName() {
        return VoterName;
    }

    public void setVoterName(String voterName) {
        VoterName = voterName;
    }

    public String getVoterSurname() {
        return VoterSurname;
    }

    public void setVoterSurname(String voterSurname) {
        VoterSurname = voterSurname;
    }

    public String getVoterEmail() {
        return VoterEmail;
    }

    public void setVoterEmail(String voterEmail) {
        VoterEmail = voterEmail;
    }

    public String getVoterPhone() {
        return VoterPhone;
    }

    public void setVoterPhone(String voterPhone) {
        VoterPhone = voterPhone;
    }

    public int getVotingState() {
        return VotingState;
    }

    public void setVotingState(int votingState) {
        VotingState = votingState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(VoterId);
        dest.writeString(VoterName);
        dest.writeString(VoterSurname);
        dest.writeString(VoterEmail);
        dest.writeString(VoterPhone);
        dest.writeInt(VotingState);
    }
}
