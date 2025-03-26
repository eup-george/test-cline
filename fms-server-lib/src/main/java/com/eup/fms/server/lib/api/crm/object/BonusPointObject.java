package com.eup.fms.server.lib.api.crm.object;

public class BonusPointObject {

    private int remainBonus;
    private int positiveBonus;
    private int negativeBonus;
    private int carOwnerGasBonus;
    private boolean haveCarOwnerContract;

    public int getRemainBonus() {
        return remainBonus;
    }

    public void setRemainBonus(int remainBonus) {
        this.remainBonus = remainBonus;
    }

    public int getPositiveBonus() {
        return positiveBonus;
    }

    public void setPositiveBonus(int positiveBonus) {
        this.positiveBonus = positiveBonus;
    }

    public int getNegativeBonus() {
        return negativeBonus;
    }

    public void setNegativeBonus(int negativeBonus) {
        this.negativeBonus = negativeBonus;
    }

    public int getCarOwnerGasBonus() {
        return carOwnerGasBonus;
    }

    public void setCarOwnerGasBonus(int carOwnerGasBonus) {
        this.carOwnerGasBonus = carOwnerGasBonus;
    }

    public boolean getHaveCarOwnerContract() {
        return haveCarOwnerContract;
    }

    public void setHaveCarOwnerContract(boolean haveCarOwnerContract) {
        this.haveCarOwnerContract = haveCarOwnerContract;
    }
}
