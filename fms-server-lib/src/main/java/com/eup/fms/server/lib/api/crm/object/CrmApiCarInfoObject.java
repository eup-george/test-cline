package com.eup.fms.server.lib.api.crm.object;

import lombok.Data;

@Data
public class CrmApiCarInfoObject {

    private String carUnicode;
    private String valueNow;
    private String valueUpdated;
    private Integer syncStatus;
    private String buzzSettingName;
}
