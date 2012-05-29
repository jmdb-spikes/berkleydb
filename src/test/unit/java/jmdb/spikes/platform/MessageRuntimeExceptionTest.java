package jmdb.spikes.platform;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MessageRuntimeExceptionTest {

    @Test
    public void formats_message() {
        MessageRuntimeException e = new MessageRuntimeException("hello [%s, %s]", "bob", "foo");
        assertThat(e.getMessage(), is("hello [bob, foo]"));
    }
}