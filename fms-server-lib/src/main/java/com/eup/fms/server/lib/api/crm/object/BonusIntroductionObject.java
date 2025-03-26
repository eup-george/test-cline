package com.eup.fms.server.lib.api.crm.object;

import java.util.List;

public class BonusIntroductionObject {

    private boolean type;
    private int id;
    private String date;
    private String companyName;
    private String name;
    private String memo;
    private String phone;
    private int percentage;
    private List<BonusIntroductionDetailObject> bonusDetail;

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public List<BonusIntroductionDetailObject> getBonusDetail() {
        return bonusDetail;
    }

    public void setBonusDetail(List<BonusIntroductionDetailObject> bonusDetail) {
        this.bonusDetail = bonusDetail;
    }
}
