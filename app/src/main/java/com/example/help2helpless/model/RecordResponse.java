package com.example.help2helpless.model;

import java.util.ArrayList;

public class RecordResponse {
    public boolean isHas_more() {
        return has_more;
    }

    public String getMessage() {
        return message;
    }

    boolean has_more;
    String message;
    ArrayList<DonarSendRecord> records=new ArrayList<>();
    public ArrayList<DonarSendRecord> getRecordsList() {
        return records;
    }
}
