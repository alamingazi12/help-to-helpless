package com.example.help2helpless.model;

import java.util.ArrayList;

public class DealersRequest {
    public ArrayList<Dealer> getDealers() {
        return dealers;
    }

    public void setDealers(ArrayList<Dealer> dealers) {
        this.dealers = dealers;
    }

    ArrayList<Dealer> dealers=new ArrayList<>();
}
