package com.example.help2helpless.model;

import java.util.ArrayList;

public class DealerResponse {
    ArrayList<Dealer> dealers=new ArrayList<>();

    public ArrayList<Dealer> getDealers() {
        return dealers;
    }

    public void setDealers(ArrayList<Dealer> dealers) {
        this.dealers = dealers;
    }
}
