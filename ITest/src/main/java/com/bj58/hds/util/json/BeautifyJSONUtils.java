package com.bj58.hds.util.json;

import java.util.List;
import java.util.Map;

public class BeautifyJSONUtils {

    private static final char SPACE_CHAR = ' ';

    private static final int INDENT_SIZE = 2;

    //当前缩进深度
    private static int callDepth = 0;

    public static String beautify(JSONObject jsonObject) {

        StringBuilder sb = new StringBuilder();
        sb.append(getIndentString());
        sb.append("{");
        callDepth++;

        List<Map.Entry<String, Object>> keyValues = jsonObject.getAllKeyValue();
        int size = keyValues.size();
        for (int i = 0; i < size; i++) {
            Map.Entry<String, Object> keyValue = keyValues.get(i);

            String key = keyValue.getKey();
            Object value = keyValue.getValue();

            sb.append("\n");
            sb.append(getIndentString());
            sb.append("\"");
            sb.append(key);
            sb.append("\"");
            sb.append(": ");

            //处理value
            if (value instanceof JSONObject) {//value为json对象
                sb.append("\n");
                sb.append(beautify((JSONObject) value));
            } else if (value instanceof JSONArray){//value为json数组
                sb.append("\n");
                sb.append(beautify((JSONArray) value));
            } else if (value instanceof String) {//value为字符串
                sb.append("\"");
                sb.append(value);
                sb.append("\"");
            } else {//value为数字、布尔值等
                sb.append(value);
            }

            if (i < size - 1) {//逗号分隔
                sb.append(",");
            }
        }

        callDepth--;
        sb.append("\n");
        sb.append(getIndentString());
        sb.append("}");

        return sb.toString();
    }

    public static String beautify(JSONArray jsonArrray) {
        StringBuilder sb = new StringBuilder();
        sb.append(getIndentString());
        sb.append("[");
        callDepth++;

        int size = jsonArrray.size();
        for (int i = 0; i < size; i++) {

            sb.append("\n");

            Object ele = jsonArrray.get(i);
            if (ele instanceof JSONObject) {
                sb.append(beautify((JSONObject) ele));
            } else if (ele instanceof JSONArray) {
                sb.append(beautify((JSONArray) ele));
            } else if (ele instanceof String) {
                sb.append(getIndentString());
                sb.append("\"");
                sb.append(ele);
                sb.append("\"");
            } else {
                sb.append(getIndentString());
                sb.append(ele);
            }

            if (i < size - 1) {
                sb.append(",");
            }
        }

        callDepth--;
        sb.append("\n");
        sb.append(getIndentString());
        sb.append("]");

        return sb.toString();
    }

    //缩进
    private static String getIndentString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < callDepth * INDENT_SIZE; i++) {
            sb.append(SPACE_CHAR);
        }

        return sb.toString();
    }
}
