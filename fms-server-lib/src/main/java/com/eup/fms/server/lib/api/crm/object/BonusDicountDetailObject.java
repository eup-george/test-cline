package com.eup.fms.server.lib.api.crm.object;

import java.util.List;

public class BonusDicountDetailObject {

    private String type;
    private int sourcetype;
    private String date;
    private String companyName;
    private String qU_ID;
    private int deviceCount;
    private double bonus;
    private double bonusSum;
    private String memo;
    private String gasContractNumber;
    private List<BonusIntroductionDetailObject> bonusDetail;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSourceType() {
        return sourcetype;
    }

    public void setSourceType(int sourcetype) {
        this.sourcetype = sourcetype;
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

    public String getQuId() {
        return qU_ID;
    }

    public void setQuId(String qU_ID) {
        this.qU_ID = qU_ID;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getBonusSum() {
        return bonusSum;
    }

    public void setBonusSum(double bonusSum) {
        this.bonusSum = bonusSum;
    }

    public String getMemo() {
        return memo;
    }

    public void setGasContractNumber(String gasContractNumber) {
        this.gasContractNumber = gasContractNumber;
    }

    public String getGasContractNumber() {
        return gasContractNumber;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<BonusIntroductionDetailObject> getBonusDetail() {
        return bonusDetail;
    }

    public void setBonusDetail(List<BonusIntroductionDetailObject> bonusDetail) {
        this.bonusDetail = bonusDetail;
    }
}
