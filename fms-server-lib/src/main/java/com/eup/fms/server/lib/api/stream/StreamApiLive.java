package com.eup.fms.server.lib.api.stream;

import com.eup.core.component.tool.Eup_DateTime;
import com.eup.core.component.tool.Eup_JSONObject;
import com.eup.fms.server.lib.api.stream.object.StreamApiObject;
import com.eup.fms.server.lib.dvr.dvrdevice.objects.Dvr2ChDeviceChannelObject;
import com.eup.fms.server.lib.dvr.dvrdevice.objects.Dvr2ChDeviceObject;
import com.eup.fms.server.lib.golbal_value.object.GlobalDvr2ChNowUrlObject;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StreamApiLive extends StreamApiOperationBase {

    public GlobalDvr2ChNowUrlObject getStreamLive(String unicode, List<Integer> channelList, GlobalDvr2ChNowUrlObject cacheObj) throws Exception {
        if (channelList == null || channelList.isEmpty()) {
            return null;
        }
        StringBuilder channelParam = new StringBuilder();
        for (int i = 0; i < channelList.size(); i++) {
            if (i == channelList.size() - 1) {
                channelParam.append(channelList.get(i));
            } else {
                channelParam.append(channelList.get(i)).append(",");
            }
        }
        if (cacheObj == null) {
            cacheObj = new GlobalDvr2ChNowUrlObject();
        }
        Dvr2ChDeviceObject chDvrDeviceObject = cacheObj.getChDvrDeviceObject();

        String path = "/picture/dvr/" + unicode + "/channels/live?channelList=" + channelParam;
        StreamApiObject streamApiObject = callStreamApi(path, null);
        List<Eup_JSONObject> resultList = streamApiObject.getResult();
        for (Eup_JSONObject resultJson : resultList) {
            int channel = resultJson.getInt("channel");
            String dTime = Objects.toString(resultJson.get("dtime"), "");
            if (dTime.isEmpty() || dTime.equals("null")) {
                continue;
            }
            if (chDvrDeviceObject == null) {
                chDvrDeviceObject = new Dvr2ChDeviceObject();
                chDvrDeviceObject.setUnicode(unicode);
                chDvrDeviceObject.setChannelMap(new HashMap<>());
            }
            String picUrl = Objects.toString(resultJson.get("picUrl"), "");
            Dvr2ChDeviceChannelObject channelObject = new Dvr2ChDeviceChannelObject();
            channelObject.setChannel(channel);
            channelObject.setLogDTime(dTime);
            channelObject.setUrl(picUrl);
            chDvrDeviceObject.getChannelMap().put(channel, channelObject);
        }
        if (chDvrDeviceObject != null) {
            for (Integer channel : channelList) {
                cacheObj.getChannelUpdateTimeMap().put(channel, new Eup_DateTime());
            }
            cacheObj.setUnicode(unicode);
            cacheObj.setChDvrDeviceObject(chDvrDeviceObject);
        }
        return cacheObj;
    }

}
