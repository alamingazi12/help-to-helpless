package com.example.help2helpless.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable {
    String cName;

    protected Client(Parcel in) {
        cName = in.readString();
        fhName = in.readString();
        profesion = in.readString();
        cage = in.readString();
        mincome = in.readString();
        src_income = in.readString();
        nson = in.readString();
        ndaughter = in.readString();
        cguardian = in.readString();
        cgardianno = in.readString();
        client_bkno = in.readString();
        cdisase = in.readString();
        cmedicost = in.readString();
        caddres = in.readString();
        cphoto = in.readString();
        cnumber = in.readString();
        dcontact = in.readString();
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

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getFhName() {
        return fhName;
    }

    public void setFhName(String fhName) {
        this.fhName = fhName;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCage() {
        return cage;
    }

    public void setCage(String cage) {
        this.cage = cage;
    }

    public String getMincome() {
        return mincome;
    }

    public void setMincome(String mincome) {
        this.mincome = mincome;
    }

    public String getSrc_income() {
        return src_income;
    }

    public void setSrc_income(String src_income) {
        this.src_income = src_income;
    }

    public String getNson() {
        return nson;
    }

    public void setNson(String nson) {
        this.nson = nson;
    }

    public String getNdaughter() {
        return ndaughter;
    }

    public void setNdaughter(String ndaughter) {
        this.ndaughter = ndaughter;
    }

    public String getCguardian() {
        return cguardian;
    }

    public void setCguardian(String cguardian) {
        this.cguardian = cguardian;
    }

    public String getCgardianno() {
        return cgardianno;
    }

    public void setCgardianno(String cgardianno) {
        this.cgardianno = cgardianno;
    }

    public String getClient_bkno() {
        return client_bkno;
    }

    public void setClient_bkno(String client_bkno) {
        this.client_bkno = client_bkno;
    }

    public String getCdisase() {
        return cdisase;
    }

    public void setCdisase(String cdisase) {
        this.cdisase = cdisase;
    }

    public String getCmedicost() {
        return cmedicost;
    }

    public void setCmedicost(String cmedicost) {
        this.cmedicost = cmedicost;
    }

    public String getCaddres() {
        return caddres;
    }

    public void setCaddres(String caddres) {
        this.caddres = caddres;
    }

    public String getCphoto() {
        return cphoto;
    }

    public void setCphoto(String cphoto) {
        this.cphoto = cphoto;
    }

    public String getCnumber() {
        return cnumber;
    }

    public void setCnumber(String cnumber) {
        this.cnumber = cnumber;
    }

    public String getDcontact() {
        return dcontact;
    }

    public void setDcontact(String dcontact) {
        this.dcontact = dcontact;
    }

    String fhName;
    String profesion;
    String cage;
    String mincome;
    String src_income;
    String nson;
    String ndaughter;
    String cguardian;
    String cgardianno;
    String client_bkno;
    String cdisase;
    String cmedicost;
    String caddres;
    String cphoto;
    String cnumber;
    String dcontact;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cName);
        parcel.writeString(fhName);
        parcel.writeString(profesion);
        parcel.writeString(cage);
        parcel.writeString(mincome);
        parcel.writeString(src_income);
        parcel.writeString(nson);
        parcel.writeString(ndaughter);
        parcel.writeString(cguardian);
        parcel.writeString(cgardianno);
        parcel.writeString(client_bkno);
        parcel.writeString(cdisase);
        parcel.writeString(cmedicost);
        parcel.writeString(caddres);
        parcel.writeString(cphoto);
        parcel.writeString(cnumber);
        parcel.writeString(dcontact);
    }
}
