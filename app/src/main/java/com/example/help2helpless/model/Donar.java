package com.example.help2helpless.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Donar implements Parcelable {
    protected Donar(Parcel in) {
        dname = in.readString();
        professions = in.readString();
        dcontact = in.readString();
        demail = in.readString();
        presentaddr = in.readString();
        zilla = in.readString();
        thana = in.readString();
        mdonation_aamount = in.readString();
        usernm = in.readString();
        passwrd = in.readString();
        donar_photo=in.readString();
    }

    public static final Creator<Donar> CREATOR = new Creator<Donar>() {
        @Override
        public Donar createFromParcel(Parcel in) {
            return new Donar(in);
        }

        @Override
        public Donar[] newArray(int size) {
            return new Donar[size];
        }
    };

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getProfessions() {
        return professions;
    }

    public void setProfessions(String professions) {
        this.professions = professions;
    }

    public String getDcontact() {
        return dcontact;
    }

    public void setDcontact(String dcontact) {
        this.dcontact = dcontact;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getPresentaddr() {
        return presentaddr;
    }

    public void setPresentaddr(String presentaddr) {
        this.presentaddr = presentaddr;
    }

    public String getZilla() {
        return zilla;
    }

    public void setZilla(String zilla) {
        this.zilla = zilla;
    }

    public String getThana() {
        return thana;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    public String getMdonation_aamount() {
        return mdonation_aamount;
    }

    public void setMdonation_aamount(String mdonation_aamount) {
        this.mdonation_aamount = mdonation_aamount;
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

    String dname;
    String professions;
    String dcontact;
    String demail;
    String presentaddr;
    String zilla;
    String thana;
    String mdonation_aamount;
    String usernm;
    String passwrd;

    public String getDonar_photo() {
        return donar_photo;
    }

    public void setDonar_photo(String donar_photo) {
        this.donar_photo = donar_photo;
    }

    String donar_photo;

    public Donar(String dname, String professions, String dcontact, String demail, String presentaddr, String zilla, String thana, String mdonation_aamount, String usernm, String passwrd,String donar_photo) {
        this.dname = dname;
        this.professions = professions;
        this.dcontact = dcontact;
        this.demail = demail;
        this.presentaddr = presentaddr;
        this.zilla = zilla;
        this.thana = thana;
        this.mdonation_aamount = mdonation_aamount;
        this.usernm = usernm;
        this.passwrd = passwrd;
        this.donar_photo=donar_photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dname);
        parcel.writeString(professions);
        parcel.writeString(dcontact);
        parcel.writeString(demail);
        parcel.writeString(presentaddr);
        parcel.writeString(zilla);
        parcel.writeString(thana);
        parcel.writeString(mdonation_aamount);
        parcel.writeString(usernm);
        parcel.writeString(passwrd);
        parcel.writeString(donar_photo);
    }
}
