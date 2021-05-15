package com.example.help2helpless.model;

import java.util.ArrayList;

public class DealerReciveResponse {

    public ArrayList<DealerReceiveRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<DealerReceiveRecord> records) {
        this.records = records;
    }

    ArrayList<DealerReceiveRecord> records=new ArrayList<>();
}
