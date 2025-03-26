package com.eup.fms.server.lib.api.crm.object;

import java.util.List;

public class DvrDeviceTypeObject {

    private String unicode;
    private List<Integer> channelLs;

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public List<Integer> getChannelLs() {
        return channelLs;
    }

    public void setChannelLs(List<Integer> channelLs) {
        this.channelLs = channelLs;
    }
}
