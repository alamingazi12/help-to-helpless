package com.example.help2helpless.model;

public class Donar {
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

    public Donar(String dname, String professions, String dcontact, String demail, String presentaddr, String zilla, String thana, String mdonation_aamount, String usernm, String passwrd) {
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
    }
}
