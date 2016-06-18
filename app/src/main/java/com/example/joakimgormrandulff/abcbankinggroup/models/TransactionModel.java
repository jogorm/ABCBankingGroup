package com.example.joakimgormrandulff.abcbankinggroup.models;

/**
 * Created by joakimgormrandulff on 20.01.2016.
 */
public class TransactionModel {

    private int trans_id;
    private int acc_id;
    private int amount;
    private String dateTime;
    private String cleanDateTime;

    public String getCleanDateTime() {
        return cleanDateTime;
    }

    public void setCleanDateTime(String cleanDateTime) {
        this.cleanDateTime = cleanDateTime;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(int trans_id) {
        this.trans_id = trans_id;
    }




}
