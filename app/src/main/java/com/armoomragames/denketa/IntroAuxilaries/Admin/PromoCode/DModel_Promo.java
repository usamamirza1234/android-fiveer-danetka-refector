package com.armoomragames.denketa.IntroAuxilaries.Admin.PromoCode;

import java.util.Date;

public class DModel_Promo {
    int id;
    int discount;
    String endDate;
    String promoCode;
    String startDate;
    boolean status;
    int updatedTime;
    String redumption;

    public DModel_Promo(int id, int discount, String endDate, String promoCode, String startDate, boolean status, int updatedTime, String redumption) {
        this.id = id;
        this.discount = discount;
        this.endDate = endDate;
        this.promoCode = promoCode;
        this.startDate = startDate;
        this.status = status;
        this.updatedTime = updatedTime;
        this.redumption = redumption;
    }

    public DModel_Promo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(int updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRedumption() {
        return redumption;
    }

    public void setRedumption(String redumption) {
        this.redumption = redumption;
    }
}
