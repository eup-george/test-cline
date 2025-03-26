package com.eup.fms.server.lib.api.stream;

import com.eup.core.component.tool.Eup_JSONObject;
import com.eup.fms.server.lib.api.stream.object.StreamApiObject;
import com.eup.fms.server.lib.dvr.dvrdevice.objects.Dvr2ChDeviceChannelObject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;
import java.util.Map;

public class StreamApiHistory extends StreamApiOperationBase {

    public void getHistory(String unicode, int channel, Map<String, Dvr2ChDeviceChannelObject> fileRecordMap)
        throws Exception {
        Eup_JSONObject jsonObject = new Eup_JSONObject();
        jsonObject.put("unicode" , unicode);
        jsonObject.put("chan" , channel);
        //create JSONArray
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Dvr2ChDeviceChannelObject> entry : fileRecordMap.entrySet()) {
            JSONObject picObj = new JSONObject();
            picObj.put("picTime" , entry.getValue().getLogDTime());
            picObj.put("picName" , entry.getValue().getPicName());
            picObj.put("picPath" , entry.getValue().getPicPath());
            jsonArray.put(picObj);
        }
        if (jsonArray.length() <= 0) {
            return;
        }
        jsonObject.put("sliceList" , jsonArray);

        String path = "/picture/dvr/" + unicode + "/channel/" + channel + "/history";
        StreamApiObject streamApiObject = callStreamApi(path, jsonObject);
        List<Eup_JSONObject> resultList = streamApiObject.getResult();
        for (Eup_JSONObject resultJson : resultList) {
            String picName = resultJson.getString("picName");
            String picUrl = resultJson.getString("picUrl");
            Dvr2ChDeviceChannelObject channelObject = fileRecordMap.get(picName);
            if (channelObject != null) {
                channelObject.setUrl(picUrl);
            }
        }
    }

}
