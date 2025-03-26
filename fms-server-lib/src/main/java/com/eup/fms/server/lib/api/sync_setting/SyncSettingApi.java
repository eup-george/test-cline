package com.eup.fms.server.lib.api.sync_setting;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.server_setting.ServerSetting;
import com.eup.fms.server.lib.dvr.component.new_playback_download.request_object.DvrControlCameraParam;
import com.eup.fms.server.lib.sync.bo.SyncConfigBo;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SyncSettingApi {

    private final String token = "7kpfMCAm6Ub4b6e9f579fe4f2093bdab4c625ea7ac3ZcvUD";

    public String dvrPostByPass(DvrControlCameraParam param) {
        String urlSuffix = "/device/camera/control";
        String postData = new Gson().toJson(param);
        return doPost(urlSuffix, postData);
    }

    private String doPost(String urlSuffix, String postData) {
        StringBuilder response = new StringBuilder();
        try {
            String urlStr = ServerSetting.getInstance().getServiceSetting().getCrmPluginSyncConfigREST().getUri().toString() + urlSuffix;
            URL url = new URL(urlStr);
            SysLogTool.getInstance().getLog().info("crmPluginSyncConfigREST: " + url + " postData: " + postData);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type" , "application/json");
            conn.setRequestProperty("Authorization" , "Bearer " + token);
            conn.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.writeBytes(postData);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public String postSettingBuzzer(SyncConfigBo param) {
        String urlSuffix = "/setting/buzzer";
        String postData = new Gson().toJson(param);
        return doPost(urlSuffix, postData);
    }

    public String doGet(String param) {
        StringBuilder response = new StringBuilder();
        try {
            String urlStr = ServerSetting.getInstance().getServiceSetting().getCrmPluginSyncConfigREST().getUri().toString() + param;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type" , "application/json");
            conn.setRequestProperty("Authorization" , "Bearer " + token);
            conn.setDoOutput(true);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
