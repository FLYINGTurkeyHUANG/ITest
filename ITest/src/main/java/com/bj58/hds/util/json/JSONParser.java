package com.bj58.hds.util.json;

import java.io.IOException;
import java.io.StringReader;

public class JSONParser {
    private JSONTokenizer tokenizer = new JSONTokenizer();

    private Parser parser = new Parser();

    public Object fromJSON(String json) throws IOException {
        CharReader charReader = new CharReader(new StringReader(json));
        JSONTokenList tokens = tokenizer.tokenize(charReader);
        return parser.parse(tokens);
    }
}
