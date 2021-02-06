package com.example.help2helpless.model;

public class Admin {

    String id;
    String aname;
    String apass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getApass() {
        return apass;
    }

    public void setApass(String apass) {
        this.apass = apass;
    }



    public Admin(String id, String aname, String apass) {
        this.id = id;
        this.aname = aname;
        this.apass = apass;
    }
}
