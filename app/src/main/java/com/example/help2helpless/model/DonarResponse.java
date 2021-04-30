package com.example.help2helpless.model;

import java.util.ArrayList;

public class DonarResponse {
     String message;

    public String getMessage() {
        return message;
    }

    public boolean isHas_more() {
        return has_more;
    }

    boolean has_more;
    ArrayList<Donar> users=new ArrayList<>();

    public ArrayList<Donar> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Donar> users) {
        this.users = users;
    }
}
