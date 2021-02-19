package com.example.help2helpless.model;

import java.util.ArrayList;

public class ClientResponse {

    public ArrayList<Client> getResult() {
        return result;
    }

    public void setResult(ArrayList<Client> result) {
        this.result = result;
    }

    ArrayList<Client> result=new ArrayList<>();
}
