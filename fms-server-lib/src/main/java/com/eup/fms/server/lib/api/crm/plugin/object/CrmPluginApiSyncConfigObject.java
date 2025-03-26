package com.eup.fms.server.lib.api.crm.plugin.object;

import com.eup.fms.base.lib.extension_methods.EupObjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(allowSetters = true)
public class CrmPluginApiSyncConfigObject {

    @JsonProperty("DISPLAY_NAME")
    private String displayName;
    @JsonProperty("SETTING_NAME")
    private String settingName;
    @JsonProperty("CMD_SELECTOR")
    private String cmdSelector;
    @JsonProperty("IS_CMD_EXIST")
    private Boolean cmdExist;
    @JsonProperty("IS_CMD_ACTIVATE")
    private Boolean cmdActivate;
    @JsonProperty("IS_CMD_IMMEDIATE")
    private Boolean cmdImmediate;
    @JsonProperty("IS_ONLY_12000CMD")
    private Boolean only12000Cmd;
    @JsonProperty("SYNC_STATUS")
    private Integer syncStatus;
    @JsonProperty("UPDATE_TIME_VALUE_NOW")
    private String updateTimeValueNow;
    @JsonProperty("UPDATE_TIME_VALUE_UPDATED")
    private String updateTimeValueUpdated;
    @JsonProperty("SETCMD_UPDATE_TIME")
    private String setCmdUpdateTime;
    @JsonProperty("SETCMD_SEND_TIME")
    private String setCmdSendTime;
    @JsonProperty("SETCMD_SEND_COUNT")
    private Integer setCmdSendCount;
    @JsonProperty("GETCMD_UPDATE_TIME")
    private String getCmdUpdateTime;
    @JsonProperty("GETCMD_SEND_TIME")
    private String getCmdSendTime;
    @JsonProperty("GETCMD_SEND_COUNT")
    private Integer getCmdSendCount;
    @JsonProperty("EDITOR")
    private String EDITOR;
    @JsonProperty("SETTING_DETAILS")
    private List<CrmPluginApiSettingDetailObject> settingDetailList;

    public CrmPluginApiSyncConfigObject(JSONObject peripherals) throws Exception {
        this.displayName = EupObjects.toString(peripherals.get("DISPLAY_NAME"));
        this.settingName = EupObjects.toString(peripherals.get("SETTING_NAME"));
        this.cmdSelector = EupObjects.toString(peripherals.get("CMD_SELECTOR"));
        this.cmdExist = EupObjects.toBoolean(peripherals.get("IS_CMD_EXIST"));
        this.cmdActivate = EupObjects.toBoolean(peripherals.get("IS_CMD_ACTIVATE"));
        this.cmdImmediate = EupObjects.toBoolean(peripherals.get("IS_CMD_IMMEDIATE"));
        this.only12000Cmd = EupObjects.toBoolean(peripherals.get("IS_ONLY_12000CMD"));
        this.syncStatus = EupObjects.toInteger(peripherals.get("SYNC_STATUS"));
        this.updateTimeValueNow = EupObjects.toString(peripherals.get("UPDATE_TIME_VALUE_NOW"));
        this.updateTimeValueUpdated = EupObjects.toString(peripherals.get("UPDATE_TIME_VALUE_UPDATED"));
        this.setCmdUpdateTime = EupObjects.toString(peripherals.get("SETCMD_UPDATE_TIME"));
        this.setCmdSendTime = EupObjects.toString(peripherals.get("SETCMD_SEND_TIME"));
        this.setCmdSendCount = EupObjects.toInteger(peripherals.get("SETCMD_SEND_COUNT"));
        this.getCmdUpdateTime = EupObjects.toString(peripherals.get("GETCMD_UPDATE_TIME"));
        this.getCmdSendTime = EupObjects.toString(peripherals.get("GETCMD_SEND_TIME"));
        this.getCmdSendCount = EupObjects.toInteger(peripherals.get("GETCMD_SEND_COUNT"));
        this.EDITOR = EupObjects.toString(peripherals.get("EDITOR"));
        this.settingDetailList = new ArrayList<>();
        JSONArray settingDetails = peripherals.getJSONArray("SETTING_DETAILS");
        for (int i = 0; i < settingDetails.length(); i++) {
            JSONObject jsonObject = settingDetails.getJSONObject(i);
            CrmPluginApiSettingDetailObject settingDetailObject = new CrmPluginApiSettingDetailObject(jsonObject);
            this.settingDetailList.add(settingDetailObject);
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getCmdSelector() {
        return cmdSelector;
    }

    public void setCmdSelector(String cmdSelector) {
        this.cmdSelector = cmdSelector;
    }

    public Boolean getCmdExist() {
        return cmdExist;
    }

    public void setCmdExist(Boolean cmdExist) {
        this.cmdExist = cmdExist;
    }

    public Boolean getCmdActivate() {
        return cmdActivate;
    }

    public void setCmdActivate(Boolean cmdActivate) {
        this.cmdActivate = cmdActivate;
    }

    public Boolean getCmdImmediate() {
        return cmdImmediate;
    }

    public void setCmdImmediate(Boolean cmdImmediate) {
        this.cmdImmediate = cmdImmediate;
    }

    public Boolean getOnly12000Cmd() {
        return only12000Cmd;
    }

    public void setOnly12000Cmd(Boolean only12000Cmd) {
        this.only12000Cmd = only12000Cmd;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getUpdateTimeValueNow() {
        return updateTimeValueNow;
    }

    public void setUpdateTimeValueNow(String updateTimeValueNow) {
        this.updateTimeValueNow = updateTimeValueNow;
    }

    public String getUpdateTimeValueUpdated() {
        return updateTimeValueUpdated;
    }

    public void setUpdateTimeValueUpdated(String updateTimeValueUpdated) {
        this.updateTimeValueUpdated = updateTimeValueUpdated;
    }

    public String getSetCmdUpdateTime() {
        return setCmdUpdateTime;
    }

    public void setSetCmdUpdateTime(String setCmdUpdateTime) {
        this.setCmdUpdateTime = setCmdUpdateTime;
    }

    public String getSetCmdSendTime() {
        return setCmdSendTime;
    }

    public void setSetCmdSendTime(String setCmdSendTime) {
        this.setCmdSendTime = setCmdSendTime;
    }

    public Integer getSetCmdSendCount() {
        return setCmdSendCount;
    }

    public void setSetCmdSendCount(Integer setCmdSendCount) {
        this.setCmdSendCount = setCmdSendCount;
    }

    public String getGetCmdUpdateTime() {
        return getCmdUpdateTime;
    }

    public void setGetCmdUpdateTime(String getCmdUpdateTime) {
        this.getCmdUpdateTime = getCmdUpdateTime;
    }

    public String getGetCmdSendTime() {
        return getCmdSendTime;
    }

    public void setGetCmdSendTime(String getCmdSendTime) {
        this.getCmdSendTime = getCmdSendTime;
    }

    public Integer getGetCmdSendCount() {
        return getCmdSendCount;
    }

    public void setGetCmdSendCount(Integer getCmdSendCount) {
        this.getCmdSendCount = getCmdSendCount;
    }

    public String getEDITOR() {
        return EDITOR;
    }

    public void setEDITOR(String EDITOR) {
        this.EDITOR = EDITOR;
    }

    public List<CrmPluginApiSettingDetailObject> getSettingDetailList() {
        return settingDetailList;
    }

    public void setSettingDetailList(
        List<CrmPluginApiSettingDetailObject> settingDetailList) {
        this.settingDetailList = settingDetailList;
    }
}
