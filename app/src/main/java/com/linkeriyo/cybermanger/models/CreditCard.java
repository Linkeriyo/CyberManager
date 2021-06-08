package com.linkeriyo.cybermanger.models;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class CreditCard {

    private String pk;
    private String cardHolder;
    private String cardNumber;
    private String expiresMonth;
    private String expiresYear;
    private String cvv;


    public CreditCard(JSONObject json) throws JSONException {
        pk = json.getString(Tags.PK);
        cardHolder = json.getString(Tags.CARD_HOLDER);
        cardNumber = json.getString(Tags.CARD_NUMBER);
        expiresMonth = json.getString(Tags.EXPIRES_MONTH);
        expiresYear = json.getString(Tags.EXPIRES_YEAR);
        cvv = json.getString(Tags.CVV);
    }

    public CreditCard(String cardHolder, String cardNumber, String expiresMonth, String expiresYear, String cvv) {
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.expiresMonth = expiresMonth;
        this.expiresYear = expiresYear;
        this.cvv = cvv;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Tags.PK, pk);
        json.put(Tags.CARD_HOLDER, cardHolder);
        json.put(Tags.CARD_NUMBER, cardNumber);
        json.put(Tags.EXPIRES_MONTH, expiresMonth);
        json.put(Tags.EXPIRES_YEAR, expiresYear);
        json.put(Tags.CVV, cvv);
        return json;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpiresMonth() {
        return expiresMonth;
    }

    public void setExpiresMonth(String expiresMonth) {
        this.expiresMonth = expiresMonth;
    }

    public String getExpiresYear() {
        return expiresYear;
    }

    public void setExpiresYear(String expiresYear) {
        this.expiresYear = expiresYear;
    }
}
