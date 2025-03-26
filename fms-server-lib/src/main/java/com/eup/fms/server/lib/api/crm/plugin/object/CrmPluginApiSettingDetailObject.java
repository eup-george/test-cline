package com.eup.fms.server.lib.api.crm.plugin.object;

import com.eup.fms.base.lib.extension_methods.EupObjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.codehaus.jettison.json.JSONObject;

@JsonIgnoreProperties(allowSetters = true)
public class CrmPluginApiSettingDetailObject {

    @JsonProperty("DETAIL_SETTING_NAME")
    private String detailSettingName;
    @JsonProperty("VALUE_UPDATED")
    private String valueUpdated;

    public CrmPluginApiSettingDetailObject(JSONObject jsonObject) throws Exception {
        this.detailSettingName = EupObjects.toString(jsonObject.get("DETAIL_SETTING_NAME"));
        ;
        this.valueUpdated = EupObjects.toString(jsonObject.get("VALUE_UPDATED"));
        ;
    }

    public String getDetailSettingName() {
        return detailSettingName;
    }

    public void setDetailSettingName(String detailSettingName) {
        this.detailSettingName = detailSettingName;
    }

    public String getValueUpdated() {
        return valueUpdated;
    }

    public void setValueUpdated(String valueUpdated) {
        this.valueUpdated = valueUpdated;
    }
}
