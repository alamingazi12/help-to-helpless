package com.example.help2helpless.model;

import java.util.ArrayList;

public class TotalDonarResponse {

    public boolean isHas_more() {
        return has_more;
    }

    boolean has_more;

    public ArrayList<Donar> getResult() {
        return result;
    }

    public void setResult(ArrayList<Donar> result) {
        this.result = result;
    }

    ArrayList<Donar> result=new ArrayList<>();
}
