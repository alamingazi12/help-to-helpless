package com.example.help2helpless.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dealer implements Parcelable {
    String name;
    String fathername;
    String hmaddres;
    String phone;
    String bksnum;
    String email;
    String shopnme;
    String shpnmthana;
    String shpnmzilla;
    String shoppic;
    String drugsell_regnum;
    String nid;
    String nid_pic;
    String usernm;
    String passwrd;

    protected Dealer(Parcel in) {
        name = in.readString();
        fathername = in.readString();
        hmaddres = in.readString();
        phone = in.readString();
        bksnum = in.readString();
        email = in.readString();
        shopnme = in.readString();
        shpnmthana = in.readString();
        shpnmzilla = in.readString();
        shoppic = in.readString();
        drugsell_regnum = in.readString();
        nid = in.readString();
        nid_pic = in.readString();
        usernm = in.readString();
        passwrd = in.readString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getHmaddres() {
        return hmaddres;
    }

    public void setHmaddres(String hmaddres) {
        this.hmaddres = hmaddres;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBksnum() {
        return bksnum;
    }

    public void setBksnum(String bksnum) {
        this.bksnum = bksnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShopnme() {
        return shopnme;
    }

    public void setShopnme(String shopnme) {
        this.shopnme = shopnme;
    }

    public String getShpnmthana() {
        return shpnmthana;
    }

    public void setShpnmthana(String shpnmthana) {
        this.shpnmthana = shpnmthana;
    }

    public String getShpnmzilla() {
        return shpnmzilla;
    }

    public void setShpnmzilla(String shpnmzilla) {
        this.shpnmzilla = shpnmzilla;
    }

    public String getShoppic() {
        return shoppic;
    }

    public void setShoppic(String shoppic) {
        this.shoppic = shoppic;
    }

    public String getDrugsell_regnum() {
        return drugsell_regnum;
    }

    public void setDrugsell_regnum(String drugsell_regnum) {
        this.drugsell_regnum = drugsell_regnum;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNid_pic() {
        return nid_pic;
    }

    public void setNid_pic(String nid_pic) {
        this.nid_pic = nid_pic;
    }

    public String getUsernm() {
        return usernm;
    }

    public void setUsernm(String usernm) {
        this.usernm = usernm;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(fathername);
        parcel.writeString(hmaddres);
        parcel.writeString(phone);
        parcel.writeString(bksnum);
        parcel.writeString(email);
        parcel.writeString(shopnme);
        parcel.writeString(shpnmthana);
        parcel.writeString(shpnmzilla);
        parcel.writeString(shoppic);
        parcel.writeString(drugsell_regnum);
        parcel.writeString(nid);
        parcel.writeString(nid_pic);
        parcel.writeString(usernm);
        parcel.writeString(passwrd);
    }
}
