package com.example.help2helpless.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dealer implements Parcelable {

    int id;
    String name;
    String hmaddres;
    String phone;
    String email;
    String shpnmthana;
    String shpnmzilla;
    String shoppic;
    String profile_pic;
    String passwrd;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHmaddres() {
        return hmaddres;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getShpnmthana() {
        return shpnmthana;
    }

    public String getShpnmzilla() {
        return shpnmzilla;
    }

    public String getShoppic() {
        return shoppic;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public static Creator<Dealer> getCREATOR() {
        return CREATOR;
    }

    protected Dealer(Parcel in) {
        id = in.readInt();
        name = in.readString();
        hmaddres = in.readString();
        phone = in.readString();
        email = in.readString();
        shpnmthana = in.readString();
        shpnmzilla = in.readString();
        shoppic = in.readString();
        profile_pic = in.readString();
        passwrd = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(hmaddres);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(shpnmthana);
        dest.writeString(shpnmzilla);
        dest.writeString(shoppic);
        dest.writeString(profile_pic);
        dest.writeString(passwrd);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dealer> CREATOR = new Creator<Dealer>() {
        @Override
        public Dealer createFromParcel(Parcel in) {
            return new Dealer(in);
        }

        @Override
        public Dealer[] newArray(int size) {
            return new Dealer[size];
        }
    };
}
