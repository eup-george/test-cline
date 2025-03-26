package com.eup.fms.server.lib.api.csc_system.object;

public class CheckIsInsideObject {

    private boolean isInside = false;
    private CheckIsInsideEnum message = CheckIsInsideEnum.OK;
    /**
     * 在哪個工地裡面，可能為null
     */
    private Integer fid;

    public boolean isInside() {
        return isInside;
    }

    public void setInside(boolean inside) {
        isInside = inside;
    }

    public CheckIsInsideEnum getMessage() {
        return message;
    }

    public void setMessage(CheckIsInsideEnum message) {
        this.message = message;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }
}
