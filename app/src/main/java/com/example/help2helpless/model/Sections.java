package com.example.help2helpless.model;

public class Sections {

    public Sections(String division,String zilla,String thana) {
        this.thana = thana;
        this.zilla = zilla;
        this.division = division;
    }

    public String getThana() {
        return thana;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    String thana;
    String zilla;
    String division;


    public String getZilla() {
        return zilla;
    }

    public String getDivision() {
        return division;
    }

    public Sections(String division, String zilla) {
        this.division = division;

        this.zilla=zilla;
    }





}
