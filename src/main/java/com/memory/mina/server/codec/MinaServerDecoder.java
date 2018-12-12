package com.memory.mina.server.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @program socketjar
 * @Author: cui.Memory
 * @Date: 2018/12/11 18:31
 * @description:
 */
public class MinaServerDecoder implements ProtocolDecoder {
    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }

    public void dispose(IoSession ioSession) throws Exception {

    }

    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        int startPosition = ioBuffer.position();
        StringBuffer stringBuffer = new StringBuffer();

        IoBuffer newIoBuffer = ioBuffer.slice();
        byte[] dest = new byte[newIoBuffer.limit()];
        newIoBuffer.get(dest);
        stringBuffer.append(new String(dest));

        protocolDecoderOutput.write(stringBuffer.toString());
        ioBuffer.position(ioBuffer.limit());
        /*while (ioBuffer.hasRemaining()) {
            byte b = ioBuffer.get();
            if(b == '\n'){
                int currentPosition = ioBuffer.position();
                int limit = ioBuffer.limit();

                ioBuffer.position(startPosition);
                ioBuffer.limit(currentPosition);

                IoBuffer newIoBuffer = ioBuffer.slice();
                byte[] dest = new byte[newIoBuffer.limit()];
                newIoBuffer.get(dest);
                stringBuffer.append(new String(dest));

                ioBuffer.position(currentPosition);
                ioBuffer.limit(limit);
            }
        }*/
    }
}
