package com.example.help2helpless.model;

import java.util.ArrayList;

public class AddDealerList {
    boolean has_more;

    public boolean isHas_more() {
        return has_more;
    }

    public String getMessage() {
        return message;
    }

    String message;

    public ArrayList<Dealer> getDealerlist() {
        return dealerlist;
    }

    public void setDealerlist(ArrayList<Dealer> dealerlist) {
        this.dealerlist = dealerlist;
    }

    ArrayList<Dealer> dealerlist=new ArrayList<>();
}
