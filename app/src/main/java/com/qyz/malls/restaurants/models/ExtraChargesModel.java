package com.qyz.malls.restaurants.models;

public class ExtraChargesModel {
    private String charge_type;
    private String charge_amount;

    public String getCharge_amount() {
        return charge_amount;
    }

    public void setCharge_amount(String charge_amount) {
        this.charge_amount = charge_amount;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }
}
