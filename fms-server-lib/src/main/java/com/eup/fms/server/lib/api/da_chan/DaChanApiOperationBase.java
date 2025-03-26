package com.eup.fms.server.lib.api.da_chan;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.tool.Eup_JSONObject;

public class DaChanApiOperationBase {

    public DaChanApiOperationBase() {

    }

    /**
     * DaChanAPI
     */
    protected static DaChanApiObject callDaChanApi(Eup_JSONObject paramObject, String methodName, String token) throws Exception {
        DaChanApiObject daChan_API_Object = new DaChanApiObject("{}");
        String daChanAPI_Result = "{}";
        try {
            DaChanApiCommand daChanApiCommand = new DaChanApiCommand();
            daChanAPI_Result = daChanApiCommand.sendCommandToDaChanApi(paramObject.toString(), methodName, token);
            daChan_API_Object = new DaChanApiObject(daChanAPI_Result);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogTool.getInstance().getLog().error(e + "daChanAPI_Result:" + daChanAPI_Result, e);
        }
        return daChan_API_Object;
    }

    protected static Eup_JSONObject createCheckIDParamJson(String id, String pwd) {
        try {
            Eup_JSONObject postObject = new Eup_JSONObject("{}");
            postObject.put("ID" , id);
            postObject.put("PWD" , pwd);
            return postObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Eup_JSONObject createGPS_send_SMSParamJson(String tkNum, String ADD01, String VBELN, String status
        , String drivers, String mobile) {
        try {
            Eup_JSONObject postObject = new Eup_JSONObject("{}");
            postObject.put("TKNUM" , tkNum);
            postObject.put("ADD01" , ADD01);
            postObject.put("VBELN" , VBELN);
            postObject.put("STATUS" , status);
            postObject.put("DRIVERS" , drivers);
            postObject.put("MOBILE" , mobile);
            return postObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Eup_JSONObject createGPS_send_SMSParamJson(String TKNUM, String ADD01, String VBELN, String STATUS
        , String DRIVERS, String MOBILE, String E_ARRIVE_TIME) {
        try {
            Eup_JSONObject postObject = new Eup_JSONObject("{}");
            postObject.put("TKNUM" , TKNUM);
            postObject.put("ADD01" , ADD01);
            postObject.put("VBELN" , VBELN);
            postObject.put("STATUS" , STATUS);
            postObject.put("DRIVERS" , DRIVERS);
            postObject.put("MOBILE" , MOBILE);
            postObject.put("E_ARRIVE_TIME" , E_ARRIVE_TIME);
            return postObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Eup_JSONObject complete_send_SMSParamJson(String TKNUM, String ADD01, String VBELN, String STATUS
        , String DRIVERS, String MOBILE, String GPSTIME, String GPSADDR) {
        try {
            Eup_JSONObject postObject = new Eup_JSONObject("{}");
            postObject.put("TKNUM" , TKNUM);
            postObject.put("ADD01" , ADD01);
            postObject.put("VBELN" , VBELN);
            postObject.put("STATUS" , STATUS);
            postObject.put("DRIVERS" , DRIVERS);
            postObject.put("MOBILE" , MOBILE);
            postObject.put("GPSTIME" , GPSTIME);
            postObject.put("GPSADDR" , GPSADDR);
            return postObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
