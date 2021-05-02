package com.example.help2helpless.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable {
    String cName;

    public String getcName() {
        return cName;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getAddress() {
        return address;
    }

    public String getThana() {
        return thana;
    }

    public String getDistrict() {
        return district;
    }

    public String getPhone() {
        return phone;
    }

    public String getClient_type() {
        return client_type;
    }

    public String getDoc_pic() {
        return doc_pic;
    }

    public String getDcontact() {
        return dcontact;
    }

    public static Creator<Client> getCREATOR() {
        return CREATOR;
    }

    String profile_pic;
    String address;
    String thana;
    String district;
    String phone;
    String client_type;
    String doc_pic;
    String dcontact;

    protected Client(Parcel in) {
        cName = in.readString();
        profile_pic = in.readString();
        address = in.readString();
        thana = in.readString();
        district = in.readString();
        phone = in.readString();
        client_type = in.readString();
        doc_pic = in.readString();
        dcontact = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cName);
        dest.writeString(profile_pic);
        dest.writeString(address);
        dest.writeString(thana);
        dest.writeString(district);
        dest.writeString(phone);
        dest.writeString(client_type);
        dest.writeString(doc_pic);
        dest.writeString(dcontact);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
