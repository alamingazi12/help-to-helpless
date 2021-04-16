package com.example.help2helpless.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Admin implements Parcelable {

    String id;
    String ausername;
    String apassword;

    protected Admin(Parcel in) {
        id = in.readString();
        ausername = in.readString();
        apassword = in.readString();
    }

    public static final Creator<Admin> CREATOR = new Creator<Admin>() {
        @Override
        public Admin createFromParcel(Parcel in) {
            return new Admin(in);
        }

        @Override
        public Admin[] newArray(int size) {
            return new Admin[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAname() {
        return ausername;
    }

    public void setAname(String aname) {
        this.ausername = aname;
    }

    public String getApass() {
        return apassword;
    }

    public void setApass(String apass) {
        this.apassword = apass;
    }



    public Admin(String id, String aname, String apass) {
        this.id = id;
        this.ausername = aname;
        this.apassword = apass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(ausername);
        parcel.writeString(apassword);
    }
}
