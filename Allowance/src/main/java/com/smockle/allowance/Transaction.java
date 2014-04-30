package com.smockle.allowance;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private float amount;
    private String description;
    private Date date;

    public Transaction(float amount, String description, Date date) {
        this.amount = amount;
        this.description = description;
        this.date = date;
    }
}