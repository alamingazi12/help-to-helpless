package com.example.help2helpless.model;

import java.util.ArrayList;

public class AllDonar {
    boolean has_more;

    public boolean isHas_more() {
        return has_more;
    }

    public String getMessage() {
        return message;
    }

    String message;
    public ArrayList<Donar> getDealers() {
        return dealers;
    }

    public void setDealers(ArrayList<Donar> dealers) {
        this.dealers = dealers;
    }

    ArrayList<Donar> dealers=new ArrayList<>();
}
