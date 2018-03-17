package utils;

import dao.BaseDAO;
import org.json.JSONArray;

import java.util.ArrayList;

public class Request {
    enum Type {POST, GET, DELETE}

    private Type mType;
    private String mData;

    public Request() {
    }

    public Request(Type type, String data) {
        this.mType = type;
        this.mData = data;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type mType) {
        this.mType = mType;
    }

    public String getBody() {
        return mData;
    }

    private void setBody(String mBody) {
        this.mData = mBody;
    }

    public void addData(ArrayList<BaseDAO> baseDAO) {
        JSONArray jsonArray = new JSONArray();
        
        for(BaseDAO object : baseDAO) {
            jsonArray.put(object.toJSON());
        }
        
        this.setBody(jsonArray.toString());
    }
}
