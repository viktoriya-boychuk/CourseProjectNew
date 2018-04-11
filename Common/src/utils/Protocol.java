package utils;

import dao.BaseDAO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Protocol {
    public enum RequestType {POST, GET, DELETE, UPDATE, GET_COLUMN, NONE}

    private String KEY_TYPE = "Protocol-Type";
    private String KEY_BODY = "Protocol-Body";
    private String KEY_DATATYPE = "Protocol-DataType";

    private RequestType mRequestType;
    private ArrayList<BaseDAO> mData;
    private JSONObject mJSONData;
    private String mDataType;
    private String mDataString;

    public Protocol(RequestType requestType, ArrayList<BaseDAO> data) {
        this.mData = new ArrayList<>();
        this.mData.addAll(data);
        this.mDataType = data.get(0).getClass().getName();
        this.setData(mData);
        mRequestType = requestType;
    }

    public Protocol(RequestType requestType, BaseDAO data) {
        this.mData = new ArrayList<>();
        this.mData.add(data);
        this.mDataType = data.getClass().getName();
        this.setData(mData);
        mRequestType = requestType;
    }

    public Protocol(String incomingString) {
        JSONObject jsonObject = new JSONObject(incomingString);
        this.mDataType = jsonObject.getString(KEY_DATATYPE);
        try {
            this.mDataString = jsonObject.getJSONObject(KEY_BODY).toString();
            try {
                setData(mDataString);
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            Logger.logInfo("No data in request", "Ignore only if request is GET");
        }

        try {
            this.mRequestType = RequestType.valueOf(jsonObject.getString(KEY_TYPE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Protocol(Class<? extends BaseDAO> dataType) {
        this.mRequestType = RequestType.GET;
        this.mDataType = dataType.getName();
    }

    public RequestType getRequestType() {
        return (mRequestType != null) ? mRequestType : RequestType.NONE;
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
        mDataType = data.get(0).getClass().getName();
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
        mDataType = mData.get(0).getClass().getName();
    }

    public ArrayList<BaseDAO> getData() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (mData == null || mData.isEmpty()) {
            mData = new ArrayList<>();
            JSONArray jsonArray = new JSONArray((new JSONObject(mDataString).get(mDataType).toString()));
            for (int i = 0; i < jsonArray.length(); i++) {
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
        JSONObject object = new JSONObject();
        object.put(KEY_TYPE, mRequestType);
        object.put(KEY_DATATYPE, mDataType);
        object.put(KEY_BODY, mJSONData);

        return object.toString();
//        return new JSONObject().put(KEY_TYPE, mRequestType).put(KEY_DATATYPE, mDataType).put(KEY_BODY, getDataString()).toJSONString();
    }
}
