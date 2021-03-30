package com.bj58.hds.util.json;

import java.io.IOException;
import java.io.Reader;

public class CharReader {
    /** 缓冲区大小 */
    public final static int BUFFER_SIZE = 1024;

    /** 缓冲区，默认大小为BUFFER_SIZE */
    private char[] buffer;

    /** 用于读取字符流 */
    private Reader reader;

    /** 缓冲区游标 */
    private int pos;

    /** 缓冲区中的字符数 */
    private int size;

    public CharReader(Reader reader){
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
    }

    /**
     * 返回下标pos处的字符
     * */
    public char peek(){
        if(pos-1>=size){
            return (char)-1;
        }
        return buffer[Math.max(0,pos-1)];
    }

    /**
     * 返回下标pos处的字符，并后移pos
     * */
    public char next() throws IOException {
        if(!hasMore()){
            return (char)-1;
        }
        return buffer[pos++];
    }

    /**
     * 缓冲区中是否还有未读取的字符
     * */
    public boolean hasMore() throws IOException{
        if(pos < size){
            return true;
        }
        fillBuffer();
        return pos < size;
    }

    /** 回退游标 */
    public void back() {
        pos = Math.max(0, --pos);
    }

    /**
     * 填充缓冲区并重置pos、size
     * */
    void fillBuffer() throws IOException{
        int n = reader.read(buffer);
        if(n == -1){
            return ;
        }
        pos = 0;
        size = n;
    }
}
