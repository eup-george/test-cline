package com.eup.fms.server.lib.api.stream;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.server_setting.ServerSetting;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class StreamApiCommand {

    public StreamApiCommand() {

    }

    public String sendCommandToStreamApi(String path, String postData) {
        StringBuilder responseBuffer = new StringBuilder();
        try {
            URL url = new URL(ServerSetting.getInstance().getServiceSetting().getStreamVnPicSoap().getUri().toString() + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type" , "application/json;charset=UTF-8");
            conn.setRequestProperty("User-Agent" , "Mozilla/5.0");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            if (postData != null) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(postData.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            }

            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String readline;
            while ((readline = bufferReader.readLine()) != null) {
                responseBuffer.append(readline);
            }
            inputStream.close();
            conn.disconnect();
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("CRM_API_Command err" , e);
        }
        return responseBuffer.toString();
    }
}
