package com.example.help2helpless.model;
import java.util.ArrayList;

public class DealerSendResponse {
    public ArrayList<DealerSendRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<DealerSendRecord> records) {
        this.records = records;
    }

    ArrayList<DealerSendRecord> records=new ArrayList<>();

}
