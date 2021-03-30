package com.bj58.hds.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONObject {
    private Map<String, Object> map = new HashMap<String, Object>();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public List<Map.Entry<String, Object>> getAllKeyValue() {
        return new ArrayList<>(map.entrySet());
    }

    public JSONObject getJSONObject(String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Invalid key");
        }

        Object obj = map.get(key);
        if (!(obj instanceof JSONObject)) {
            throw new JSONTypeException("Type of value is not JSONObject");
        }

        return (JSONObject) obj;
    }

    public JSONArray getJSONArray(String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Invalid key");
        }

        Object obj = map.get(key);
        if (!(obj instanceof JSONArray)) {
            throw new JSONTypeException("Type of value is not JSONArray");
        }

        return (JSONArray) obj;
    }

    @Override
    public String toString() {
        return BeautifyJSONUtils.beautify(this);
    }
}
