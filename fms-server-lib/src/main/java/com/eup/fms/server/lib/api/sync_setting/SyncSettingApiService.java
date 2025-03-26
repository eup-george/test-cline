package com.eup.fms.server.lib.api.sync_setting;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.server_setting.ServerSetting;
import com.eup.fms.base.lib.http.HttpResponse;
import com.eup.fms.base.lib.http.HttpUtil;
import com.eup.fms.base.lib.http.object.InitialParam;
import com.eup.fms.base.lib.http.object.RequestParam;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class SyncSettingApiService {

    public boolean setRfidDeviceLight(RfidLightTurnOnObject param) throws MalformedURLException {
        URL url = ServerSetting.getInstance().getServiceSetting().getCrmPluginSyncConfigREST().getUri().toURL();
        String token = "Bearer " + ServerSetting.getInstance().getServiceSetting().getCrmPluginSyncConfigREST().getPassword();
        String urlSuffix = "/peripheral/rfid/light/turn-on";

        InitialParam httpUtilParam = new InitialParam();
        httpUtilParam.setBaseUrl(url.toString());
        httpUtilParam.setToken(token);
        httpUtilParam.setSetOutput(true);
        httpUtilParam.setConnectTimeout(20000);
        httpUtilParam.setReadTimeout(20000);

        HttpUtil httpUtil = new HttpUtil(httpUtilParam);
        HttpResponse httpResponse = httpUtil.post(urlSuffix ,
            new RequestParam()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .bodyParameter(param));
        if (httpResponse.getHttpCode() != 200) {
            SysLogTool.getInstance().getLog().error(httpResponse.getResponse());
        }
        return httpResponse.getHttpCode() == 200;
    }

}
