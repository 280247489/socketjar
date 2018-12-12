package com.memory.mina.server.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @program socketjar
 * @Author: cui.Memory
 * @Date: 2018/12/11 18:23
 * @description:
 */
public class MinaServerProtocolCodecFactory implements ProtocolCodecFactory {
    private MinaServerEncoder minaServerEncoder;
    private MinaServerDecoder minaServerDecoder;

    public MinaServerProtocolCodecFactory() {
        this.minaServerEncoder = new MinaServerEncoder();
        this.minaServerDecoder = new MinaServerDecoder();
    }

    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return minaServerEncoder;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return minaServerDecoder;
    }
}
