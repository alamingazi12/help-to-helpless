package com.example.help2helpless.model;

import java.util.ArrayList;

import io.reactivex.Observable;

public class DealerResponse {

    public boolean isHas_more() {
        return has_more;
    }

    boolean has_more;

    public String getMessage() {
        return message;
    }

    String message;
    ArrayList<Dealer> dealers=new ArrayList<>();

    public ArrayList<Dealer> getDealers() {
        return dealers;
    }

    public void setDealers(ArrayList<Dealer> dealers) {
        this.dealers = dealers;
    }
}
