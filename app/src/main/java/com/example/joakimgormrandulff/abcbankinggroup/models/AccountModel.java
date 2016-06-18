package com.example.joakimgormrandulff.abcbankinggroup.models;

/**
 * Created by joakimgormrandulff on 19.01.2016.
 */
public class AccountModel {

    private int AccountID;
    private String firstName;
    private String lastName;
    private String Address;
    private String postCode;
    private int telePhone;
    private int balance;
    private int pin;
    private int runningTotals;

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(int telePhone) {
        this.telePhone = telePhone;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getRunningTotals() {
        return runningTotals;
    }

    public void setRunningTotals(int runningTotals) {
        this.runningTotals = runningTotals;
    }
}
