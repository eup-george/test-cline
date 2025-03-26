package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import org.codehaus.jettison.json.JSONArray;

import java.util.List;

public class CrmApiEupIsPermissionOperation extends CrmApiOperationBase {

    public List<EupJsonObject> eupISPermissionGetAllFunction() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("EupIS_Permission_GetAllFunction" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject();
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupISPermissionAddFunction(String functionCode, String functionName) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("functionCode" , functionCode);
            paramObject.put("functionName" , functionName);
            paramObject = createParamJson("EupIS_Permission_AddFunction" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupISPermissionUpdateFunction(Integer functionId, String functionCode, String functionName, boolean status) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("functionID" , functionId);
            paramObject.put("functionCode" , functionCode);
            paramObject.put("functionName" , functionName);
            paramObject.put("status" , status);
            paramObject = createParamJson("EupIS_Permission_UpdateFunction" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupISPermissionDeleteFunction(Integer functionId) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("functionID" , functionId);
            paramObject = createParamJson("EupIS_Permission_DeleteFunction" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<EupJsonObject> eupISPermissionGetActiveAction() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("EupIS_Permission_GetActiveAction" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject();
        } catch (Exception e) {
            return null;
        }
    }

    public List<EupJsonObject> eupISPermissionGetActionByFunctionCode(String functionCode) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("functionCode" , functionCode);
            paramObject = createParamJson("EupIS_Permission_GetActionByFunctionCode" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject();
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupISPermissionAddAction(Integer functionId, String actionName, Integer type, Integer order) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("functionID" , functionId);
            paramObject.put("actionName" , actionName);
            paramObject.put("type" , type);
            paramObject.put("order" , order);
            paramObject = createParamJson("EupIS_Permission_AddAction" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupISPermissionUpdateAction(Integer actionId, Integer functionId, String actionName, Integer type, Integer order,
                                                     boolean status) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("actionID" , actionId);
            paramObject.put("functionID" , functionId);
            paramObject.put("actionName" , actionName);
            paramObject.put("type" , type);
            paramObject.put("order" , order);
            paramObject.put("status" , status);
            paramObject = createParamJson("EupIS_Permission_UpdateAction" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupIsPermissionDeleteAction(Integer actionId) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("actionID" , actionId);
            paramObject = createParamJson("EupIS_Permission_DeleteAction" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<EupJsonObject> eupIsPermissionGetGroup() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("EupIS_Permission_GetGroup" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject();
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupIsPermissionAddGroup(String groupName, JSONArray userList) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("groupName" , groupName);
            paramObject.put("userList" , userList);
            paramObject = createParamJson("EupIS_Permission_AddGroup" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupIsPermissionUpdateGroup(Integer groupId, String groupName, JSONArray userList) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("groupID" , groupId);
            paramObject.put("groupName" , groupName);
            paramObject.put("userList" , userList);
            paramObject = createParamJson("EupIS_Permission_UpdateGroup" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupIsPermissionDeleteGroup(Integer groupId) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("groupID" , groupId);
            paramObject = createParamJson("EupIS_Permission_DeleteGroup" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupIsPermissionAddUserGroup(Integer groupId, Integer userId, Integer type) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("groupID" , groupId);
            paramObject.put("userID" , userId);
            paramObject.put("type" , type);
            paramObject = createParamJson("EupIS_Permission_AddUserGroup" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupIsPermissionUpdateGroupPermission(Integer groupId, List<Integer> actionList) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("groupID" , groupId);
            paramObject.put("actionList" , actionList);
            paramObject = createParamJson("EupIS_Permission_UpdateGroupPermission" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<EupJsonObject> eupISPermissionGetAllUserPermission() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("EupIS_Permission_GetAllUserPermission" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject();
        } catch (Exception e) {
            return null;
        }
    }

    public List<EupJsonObject> eupISPermissionGetUserPermissionById(Integer userId, Integer userType) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("userID" , userId);
            paramObject.put("type" , userType);
            paramObject = createParamJson("EupIS_Permission_GetUserPermissionByID" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject();
        } catch (Exception e) {
            return null;
        }
    }

    public List<EupJsonObject> eupISPermissionGetUserPermissionByFunctionCode(String functionCode, Integer userId, Integer userType) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("functionCode" , functionCode);
            paramObject.put("userID" , userId);
            paramObject.put("type" , userType);
            paramObject = createParamJson("EupIS_Permission_GetUserPermissionByFunctionCode" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject();
        } catch (Exception e) {
            return null;
        }
    }

    public EupJsonObject eupIsPermissionUpdateUserPermission(Integer userId, Integer userType, List<Integer> actionList) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("userID" , userId);
            paramObject.put("type" , userType);
            paramObject.put("actionList" , actionList);
            paramObject = createParamJson("EupIS_Permission_UpdateUserPermission" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

}
