package com.bj58.hds.util.json;

import java.io.IOException;

public class JSONTokenizer {

    private CharReader charReader;

    private JSONTokenList tokenList;

    public JSONTokenList tokenize(CharReader charReader) throws IOException{
        this.charReader = charReader;
        tokenList = new JSONTokenList();
        tokenize();
        return tokenList;
    }

    /** 处理文本为token */
    private void tokenize() throws IOException {
        JSONToken jsonToken;
        do{
            jsonToken = readToken();
            tokenList.add(jsonToken);
        }while(jsonToken.getTokenType() != JSONTokenType.END_DOCUMENT);
    }

    private JSONToken readToken() throws  IOException{
        char ch;
        //读入一个字符若非空则终止循环，进入switch
        for(;;){
            if(!charReader.hasMore()){
                return new JSONToken(JSONTokenType.END_DOCUMENT,null);
            }
            ch = charReader.next();
            if(!isWhiteSpace(ch)){
                break;
            }
        }

        switch (ch){
            case '{':
                return new JSONToken(JSONTokenType.BEGIN_OBJECT,String.valueOf(ch));
            case '}':
                return new JSONToken(JSONTokenType.END_OBJECT,String.valueOf(ch));
            case '[':
                return new JSONToken(JSONTokenType.BEGIN_ARRAY,String.valueOf(ch));
            case ']':
                return new JSONToken(JSONTokenType.END_ARRAY,String.valueOf(ch));
            case ',':
                return new JSONToken(JSONTokenType.SEP_COMMA,String.valueOf(ch));
            case ':':
                return new JSONToken(JSONTokenType.SEP_COLON,String.valueOf(ch));
            case 'n':
                return readNull();
            case 't':
            case 'f':
                return readBoolean();
            case '"':
                return readString();
            case '-'://负数
                return readNumber();
        }
        if(isDigit(ch)){//非负数
            return readNumber();
        }
        throw new JSONParseException("Illegal character");
    }

    /** 判断空字符 */
    private boolean isWhiteSpace(char ch) {
        return (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n');
    }

    /** 读取null */
    private JSONToken readNull() throws IOException{
        if(!(charReader.next() == 'u' && charReader.next() == 'l' && charReader.next() == 'l')){
            throw new JSONParseException("Invalid json string");
        }
        return new JSONToken(JSONTokenType.NULL,"null");
    }

    /** 读取布尔值 */
    private JSONToken readBoolean() throws IOException{
        if (charReader.peek() == 't') {
            if (!(charReader.next() == 'r' && charReader.next() == 'u' && charReader.next() == 'e')) {
                throw new JSONParseException("Invalid json string");
            }

            return new JSONToken(JSONTokenType.BOOLEAN, "true");
        } else {
            if (!(charReader.next() == 'a' && charReader.next() == 'l'
                    && charReader.next() == 's' && charReader.next() == 'e')) {
                throw new JSONParseException("Invalid json string");
            }

            return new JSONToken(JSONTokenType.BOOLEAN, "false");
        }
    }

    /** 读取字符串 */
    private JSONToken readString() throws IOException{
        StringBuilder sb = new StringBuilder();
        //循环读取字符串后续内容
        for (;;) {
            char ch = charReader.next();
            if (ch == '\\') {//存在特殊转译
                if (!isEscape()) {//非法转译字符
                    throw new JSONParseException("Invalid escape character");
                }
                //转译字符处理
                sb.append('\\');
                ch = charReader.peek();
                sb.append(ch);
                //十六进制特殊处理
                if (ch == 'u') {
                    for (int i = 0; i < 4; i++) {
                        ch = charReader.next();
                        if (isHex(ch)) {
                            sb.append(ch);
                        } else {//非法的十六进制
                            throw new JSONParseException("Invalid character");
                        }
                    }
                }
            } else if (ch == '"') {// " 又读入一个"说明字符串读入结束
                return new JSONToken(JSONTokenType.STRING, sb.toString());
            } else if (ch == '\r' || ch == '\n') {//直接写入未带转译符号的转译字符，
                throw new JSONParseException("Invalid character");
            } else {//正常字符
                sb.append(ch);
            }
        }
    }

    /** 是否转译，前置字符为\ */
    private boolean isEscape() throws IOException {
        char ch = charReader.next();
        return (ch == '"' || ch == '\\' || ch == 'u' || ch == 'r'
                || ch == 'n' || ch == 'b' || ch == 't' || ch == 'f');

    }

    /** 是否为十六进制 */
    private boolean isHex(char ch) {
        return ((ch >= '0' && ch <= '9') || ('a' <= ch && ch <= 'f')
                || ('A' <= ch && ch <= 'F'));
    }

    /** 读取数字 */
    private JSONToken readNumber() throws IOException{
        char ch = charReader.peek();
        StringBuilder sb = new StringBuilder();
        if (ch == '-') {// 处理负数
            sb.append(ch);
            ch = charReader.next();
            if (ch == '0') {// 处理 -0.xxxx
                sb.append(ch);
                sb.append(readFracAndExp());
            } else if (isDigit(ch)) {
                do {
                    sb.append(ch);
                    ch = charReader.next();
                } while (isDigit(ch));
                if (ch != (char) -1) {
                    charReader.back();
                    sb.append(readFracAndExp());
                }
            } else {
                throw new JSONParseException("Invalid minus number");
            }
        } else if (ch == '0') {// 处理小数
            sb.append(ch);
            sb.append(readFracAndExp());
        } else {
            do {
                sb.append(ch);
                ch = charReader.next();
            } while (isDigit(ch));
            if (ch != (char) -1) {
                charReader.back();
                sb.append(readFracAndExp());
            }
        }

        return new JSONToken(JSONTokenType.NUMBER, sb.toString());
    }

    /** 是否为e，用于表示10的多少次幂，如1.99E10=19900000000 */
    private boolean isExp(char ch) throws IOException {
        return ch == 'e' || ch == 'E';
    }

    /** 是否为0-9数字 */
    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private String readFracAndExp() throws IOException {
        StringBuilder sb = new StringBuilder();
        char ch = charReader.next();
        if (ch ==  '.') {//小数点
            sb.append(ch);
            ch = charReader.next();
            if (!isDigit(ch)) {//不是0-9则非法
                throw new JSONParseException("Invalid frac");
            }
            do {
                sb.append(ch);
                ch = charReader.next();
            } while (isDigit(ch));

            if (isExp(ch)) {// 处理科学计数法
                sb.append(ch);
                sb.append(readExp());
            } else {
                if (ch != (char) -1) {//不是科学计数法且该字符不是空则需要回退一位
                    charReader.back();
                }
            }
        } else if (isExp(ch)) {
            sb.append(ch);
            sb.append(readExp());
        } else {
            charReader.back();
        }

        return sb.toString();
    }

    private String readExp() throws IOException {
        StringBuilder sb = new StringBuilder();
        char ch = charReader.next();
        if (ch == '+' || ch =='-') {
            sb.append(ch);
            ch = charReader.next();
            if (isDigit(ch)) {//读取科学计数法的指数值
                do {
                    sb.append(ch);
                    ch = charReader.next();
                } while (isDigit(ch));

                if (ch != (char) -1) {// 读取结束，不用回退
                    charReader.back();
                }
            } else {
                throw new JSONParseException("e or E");
            }
        } else {
            throw new JSONParseException("e or E");
        }

        return sb.toString();
    }
}
