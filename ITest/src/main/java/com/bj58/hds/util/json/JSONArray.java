package com.bj58.hds.util.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONArray implements Iterable{
    
    private List list = new ArrayList();

    public void add(Object obj) {
        list.add(obj);
    }

    public Object get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public JSONObject getJSONObject(int index) {
        Object obj = list.get(index);
        if (!(obj instanceof JSONObject)) {
            throw new JSONTypeException("Type of value is not JSONObject");
        }

        return (JSONObject) obj;
    }

    public JSONArray getJSONArray(int index) {
        Object obj = list.get(index);
        if (!(obj instanceof JSONArray)) {
            throw new JSONTypeException("Type of value is not JSONArray");
        }

        return (JSONArray) obj;
    }

    @Override
    public String toString() {
        return BeautifyJSONUtils.beautify(this);
    }

    public Iterator iterator() {
        return list.iterator();
    }
}
