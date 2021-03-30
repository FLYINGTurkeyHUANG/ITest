package com.bj58.hds.util.json;

import java.util.ArrayList;
import java.util.List;

public class JSONTokenList {

    private List<JSONToken> JSONTokens = new ArrayList<JSONToken>();

    private int pos = 0;

    public void add(JSONToken JSONToken) {
        JSONTokens.add(JSONToken);
    }

    public JSONToken peek() {
        return hasMore() ? JSONTokens.get(pos) : null;
    }

    public JSONToken peekPrevious() {
        return pos - 1 < 0 ? null : JSONTokens.get(pos - 2);
    }

    public JSONToken next() {
        return JSONTokens.get(pos++);
    }

    public boolean hasMore() {
        return pos < JSONTokens.size();
    }

    @Override
    public String toString() {
        return "JSONTokenList{" +
                "JSONTokens=" + JSONTokens +
                '}';
    }
}
