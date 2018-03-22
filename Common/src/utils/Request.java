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
    private ArrayList<BaseDAO> mData;
    private JSONObject mJSONData;
    private String mDataType;
    private String mDataString;

    public Request(RequestType requestType, ArrayList<BaseDAO> data) {
        this.mData = new ArrayList<>();
        this.mData.addAll(data);
        this.mDataType = data.get(0).getClass().getName();
        mRequestType = requestType;
    }

    public RequestType getRequestType() {
        return mRequestType;
    }

    public void setRequestType(RequestType mRequestType) {
        this.mRequestType = mRequestType;
    }

    public String getDataString() {
        return mDataString;
    }

    public void setDataString(String mDataString) {
        this.mDataString = mDataString;
    }

    public String getDataType() {
        return mDataType;
    }

    public void setDataType(String mDataType) {
        this.mDataType = mDataType;
    }

    public void setData(ArrayList<BaseDAO> data) {
        mData = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        mData.addAll(data);
        for (BaseDAO baseDAO : mData) {
            JSONObject jsonObject = baseDAO.toJSON();
            jsonArray.put(jsonObject);
        }
        mJSONData = new JSONObject();
        mJSONData.put(mDataType, jsonArray);
        mDataString = mJSONData.toString();
    }

    public void setData(String incomingString) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        mData = new ArrayList<>();
        mJSONData = new JSONObject(incomingString.trim());
        JSONArray jsonArray = new JSONArray(mJSONData.getJSONArray(mDataType).toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            BaseDAO object = parseJSONObject(jsonObject);

            mData.add(object);
        }
        mDataString = mJSONData.toString();
    }

    public ArrayList<BaseDAO> getData() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (mData == null) {
            mData = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(new JSONObject(mDataString).get(mDataType));
            for (int i = 0; i > jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                BaseDAO object = parseJSONObject(jsonObject);
                mData.add(object);
            }
            return mData;
        } else return mData;
    }

    private BaseDAO parseJSONObject(JSONObject jsonObject) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return ((BaseDAO) Class.forName(this.getDataType()).newInstance()).parseJSON(jsonObject);
    }

    @Override
    public String toString() {
        return new JSONObject().put(KEY_TYPE, mRequestType).put(KEY_BODY, getDataString()).toString();
    }
}
