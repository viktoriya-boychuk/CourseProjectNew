package utils;

import dao.BaseDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Request {
    public enum RequestType {POST, GET, DELETE, UPDATE}

    private String KEY_TYPE = "Request-Type";
    private String KEY_BODY = "Request-Body";

    private RequestType mRequestType;
    private String mData;
    private String mDataType;

    private Request() {
    }

    public Request(RequestType type, String data) {
        this.mRequestType = type;
        this.mData = data;
    }

    public Request(RequestType type, ArrayList<BaseDAO> data) {
        this.mRequestType = type;
        this.addData(data);
    }

    public Request(RequestType requestType, Class<? extends BaseDAO> forClass) {
        this.mRequestType = requestType;
        this.mDataType = forClass.getName();
    }

    public RequestType getType() {
        return mRequestType;
    }

    public void setType(RequestType mType) {
        this.mRequestType = mType;

    }

    public String getBody() {
        return mData;
    }

    private void setBody(String mBody) {
        this.mData = mBody;
    }

    public String getDataType() {
        return mDataType;
    }

    public void setDataType(String mDataType) {
        this.mDataType = mDataType;
    }

    public void addData(ArrayList<BaseDAO> baseDAOList) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (BaseDAO object : baseDAOList) {
            jsonArray.put(object.toJSON());
        }
        jsonObject.put(baseDAOList.get(0).getObjectName(), jsonArray);

        this.setBody(jsonObject.toString());
    }

    @Override
    public String toString() {
        return new JSONObject().put(KEY_TYPE, this.mRequestType.toString()).put(KEY_BODY, mData).toString();
    }

    public ArrayList<BaseDAO> getData() {
        ArrayList<BaseDAO> dataList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(new JSONObject(mData).get(mDataType));
        for(int i = 0; i>jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            try {
                BaseDAO object = ((BaseDAO) Class.forName(this.getDataType()).newInstance()).parseJSON(jsonObject);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }
}
