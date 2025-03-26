package com.eup.fms.server.lib.api.crm.plugin;

import com.eup.fms.server.lib.api.crm.plugin.object.CrmPluginApiBo;
import com.eup.fms.server.lib.api.crm.plugin.object.CrmPluginApiRestBo;

public class CrmPluginApiSerivce {

    private static final String Token = "7kpfMCAm6Ub4b6e9f579fe4f2093bdab4c625ea7ac3ZcvUD";

    public CrmPluginApiSerivce() {
    }

    /**
     * 呼叫CRM API
     */
    public CrmPluginApiBo callCrmPluginApi(String paramString) throws Exception {
        CrmPluginApiCommand crmPluginApiCommand = new CrmPluginApiCommand();
        String crmPluginApiResult = crmPluginApiCommand.sendCommandToCrmPluginApi(paramString);
        return new CrmPluginApiBo(crmPluginApiResult);
    }

    /**
     * 呼叫CRM API
     */
    public CrmPluginApiRestBo callCrmPluginRestApi(String paramString, String path, String carUnicode) throws Exception {
        CrmPluginApiCommand crmPluginApiCommand = new CrmPluginApiCommand();
        String crmPluginApiResult = crmPluginApiCommand.sendCommandToCrmPluginRestApi(paramString, path, carUnicode);
        return new CrmPluginApiRestBo(crmPluginApiResult);
    }

    /**
     * 產生Param格式
     */
    public String createParamString(String paramString) {
        try {
            paramString = paramString + "&Token=" + Token;
            return paramString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
