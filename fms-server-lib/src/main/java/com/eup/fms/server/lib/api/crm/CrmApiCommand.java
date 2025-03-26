package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.server_setting.ServerSetting;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CrmApiCommand {

    public CrmApiCommand() {

    }

    public String sendCommandToCrmApi(String postData) {
        String outdata = "";
        try {
            URL url = new URL(ServerSetting.getInstance().getServiceSetting().getCrmInnerSOAP().getUri().toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type" , "application/x-www-form-urlencoded; charset=utf-8");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes(StandardCharsets.UTF_8));
            outputStream.close();

            InputStream inputStream = conn.getInputStream();
            BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String readline = null;
            while ((readline = buffer_reader.readLine()) != null) {
                outdata += readline;
            }
            inputStream.close();
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("CRM_API_Command err" , e);
        }
        return outdata;
    }
}
