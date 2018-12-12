package com.memory.mina.server;

import org.apache.mina.core.session.IoSession;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MinaSessionCache {
    private static Map<String, IoSession> map = new ConcurrentHashMap();
    public static Map<String, IoSession> getMap() {
        return map;
    }

    private static CharsetEncoder charsetEncoder;

    public static CharsetEncoder getCharsetEncoder() {
        if(charsetEncoder == null){
             charsetEncoder = Charset.defaultCharset().newEncoder();
        }
        return charsetEncoder;
    }
}
