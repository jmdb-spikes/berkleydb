package jmdb.spikes.platform;

import static java.lang.String.format;

public class MessageRuntimeException extends RuntimeException {

    public MessageRuntimeException(String format, Object... args) {
        super(format(format, args));
    }
}