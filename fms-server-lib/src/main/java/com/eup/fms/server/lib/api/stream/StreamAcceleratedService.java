package com.eup.fms.server.lib.api.stream;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.base.lib.extension_methods.Mapper;
import com.eup.fms.dao.table.object.db.redis.DvrDisableLivePo;
import com.eup.fms.dao.table.redis.dvrstatus.DvrDisableLiveRepository;
import com.eup.fms.server.lib.golbal_value.GlobalCrmCarInfo;
import com.google.common.net.HttpHeaders;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamAcceleratedService {

    @Autowired private DvrDisableLiveRepository dvrDisableLiveRepository;

    public boolean startAcceleratedStreaming(String carUnicode, String custId, Integer durationMin) throws Exception {
        String mac = GlobalCrmCarInfo.getInstance().getMacByUnicode(carUnicode);
        Date startTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MINUTE, durationMin);
        Date endTime = calendar.getTime();
        DvrDisableLivePo dvrDisableLivePo = dvrDisableLiveRepository.get(carUnicode);
        dvrDisableLivePo.setAccelerationStartTime(startTime);
        dvrDisableLivePo.setCustId(custId);
        dvrDisableLivePo.setAccelerationEndTime(endTime);
        dvrDisableLivePo.setUnicode(carUnicode);
        dvrDisableLivePo.setStatus(true);
        dvrDisableLiveRepository.set(dvrDisableLivePo);
        String result = generateLiveStream(carUnicode, mac, "stop");
        EupJsonObject resultJson = new EupJsonObject(result);
        return resultJson.getInt("responseStatus", 0) == 100;
    }

    public boolean stopAcceleratedStreaming(String carUnicode, String custId) throws Exception {
        String mac = GlobalCrmCarInfo.getInstance().getMacByUnicode(carUnicode);
        DvrDisableLivePo dvrDisableLivePo = dvrDisableLiveRepository.get(carUnicode);
        dvrDisableLivePo.setStatus(false);
        dvrDisableLivePo.setUnicode(carUnicode);
        dvrDisableLivePo.setCustId(custId);
        dvrDisableLiveRepository.set(dvrDisableLivePo);
        generateLiveStream(carUnicode, mac, "start");
        return true;
    }

    private String generateLiveStream(String carUnicode, String mac, String key) throws Exception {
        Map<String, Object> filedMap = new HashMap<>();
        filedMap.put("unicode", carUnicode);
        filedMap.put("mac", mac);
        filedMap.put("userName", "");
        filedMap.put("nodeId", "");
        filedMap.put("nodeIp", "");
        String postData = Mapper.getMapper().writeValueAsString(filedMap);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String url = "https://hcitest-fms.eupfin.com:9443/MasterServlet/stream/live/" + key;
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(postData, "UTF-8");
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setEntity(entity);
        String basicEncoding = "Bearer 7kpfMCAm6UnKqBuHFcFrphJV0LphZl1DaG3ZcvUD";
        httpPost.addHeader(HttpHeaders.AUTHORIZATION, basicEncoding);
        CloseableHttpResponse response = client.execute(httpPost);
        return EntityUtils.toString(response.getEntity());
    }

    public DvrDisableLivePo getAcceleratedDuration(String carUnicode) throws Exception {
        return dvrDisableLiveRepository.get(carUnicode);
    }


}
