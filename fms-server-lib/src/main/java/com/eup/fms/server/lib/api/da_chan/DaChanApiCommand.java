package com.eup.fms.server.lib.api.da_chan;

import com.eup.core.component.logger.SysLogTool;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DaChanApiCommand {

    public DaChanApiCommand() {

    }

    public String sendCommandToDaChanApi(String postData, String methodName, String token) {
        String outdata = "";
        String urlAddr = "http://carapi.greatwall.com.tw/WebApiCar/WebApiCar/WebApiCar/";
        try {
            URL url = new URL(urlAddr + methodName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (!token.equals("")) {
                conn.setRequestProperty("Authorization" , "Bearer " + token);
            }
            conn.setRequestProperty("Content-Type" , "application/json; charset=utf-8");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            postData = "'" + postData + "'";
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes(StandardCharsets.UTF_8));
            outputStream.close();

            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String readline;
            while ((readline = bufferReader.readLine()) != null) {
                outdata += readline;
            }
            outdata = outdata.substring(1, outdata.length() - 1);
            outdata = outdata.replaceAll("\\\\" , "");
            inputStream.close();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            SysLogTool.getInstance().getLog().error(e);
        }
        return outdata;
    }
}
