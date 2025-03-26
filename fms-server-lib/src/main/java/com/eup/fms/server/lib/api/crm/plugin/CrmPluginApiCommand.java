package com.eup.fms.server.lib.api.crm.plugin;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.server_setting.ServerSetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CrmPluginApiCommand {

    public CrmPluginApiCommand() {

    }

    public String sendCommandToCrmPluginApi(String postData) {
        String outdata = "";
        try {
            URL url = new URL(
                ServerSetting.getInstance().getServiceSetting().getCrmPluginSyncConfig().getUri().toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type" , "application/x-www-form-urlencoded; charset=utf-8");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes(StandardCharsets.UTF_8));
            outputStream.close();

            InputStream inputStream = conn.getInputStream();
            BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String readline;
            while ((readline = buffer_reader.readLine()) != null) {
                outdata += readline;
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outdata;
    }


    public String sendCommandToCrmPluginRestApi(String postData, String path, String carUnicode) {
        String outdata = "";
        try {
            URL url = new URL(ServerSetting.getInstance().getServiceSetting().getCrmPluginSyncConfigREST().getUri().toString() + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String bearer = "Bearer " + ServerSetting.getInstance().getServiceSetting().getCrmPluginSyncConfigREST().getPassword();
            conn.addRequestProperty("Authorization" , bearer);
            conn.addRequestProperty("Content-Type" , "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes(StandardCharsets.UTF_8));
            outputStream.close();

            InputStream inputStream = conn.getInputStream();
            BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String readline;
            while ((readline = buffer_reader.readLine()) != null) {
                outdata += readline;
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> logInfo = new HashMap<>();
        logInfo.put("outdata" , outdata);
        logInfo.put("unicode" , carUnicode);
        SysLogTool.getInstance().getLog().info(logInfo, "checkCutOffOilPower");
        return outdata;
    }
}
