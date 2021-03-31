package com.example.help2helpless.model;

import java.util.ArrayList;

public class AddDealerList {

    public ArrayList<Dealer> getDealerlist() {
        return dealerlist;
    }

    public void setDealerlist(ArrayList<Dealer> dealerlist) {
        this.dealerlist = dealerlist;
    }

    ArrayList<Dealer> dealerlist=new ArrayList<>();
}
