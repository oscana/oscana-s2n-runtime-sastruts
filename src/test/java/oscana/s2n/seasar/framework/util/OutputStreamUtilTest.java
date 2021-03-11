package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

/**
 * {@link OutputStreamUtil}のテスト。
 *
 */
public class OutputStreamUtilTest {

    OutputStreamUtil outputStreamUtil = new OutputStreamUtil();

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    @SuppressWarnings("static-access")
    public void testClose() throws Exception {
        NotifyOutputStream out = new NotifyOutputStream();
        outputStreamUtil.close(out);
        assertEquals("closed", out.getNotify());
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    @SuppressWarnings("static-access")
    public void testCloseNull() throws Exception {
        outputStreamUtil.close((OutputStream) null);
        assertTrue(true);
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    @SuppressWarnings("static-access")
    public void testClose_throwIOException() throws Exception {
        OutputStream out = new IOExceptionOccurOutputStream();
        try {
            outputStreamUtil.close(out);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * 出力ストリームのオブジェクトが存在している場合、flushできること
     * @throws Exception
     */
    @Test
    @SuppressWarnings("static-access")
    public void testFlush() throws Exception {
        NotifyOutputStream out = new NotifyOutputStream();
        outputStreamUtil.flush(out);
        assertEquals("flushed", out.getNotify());
    }

    /**
     * 出力ストリームのオブジェクトがnullの場合、何も戻さないこと
     * @throws Exception
     */
    @Test
    @SuppressWarnings("static-access")
    public void testFlushNull() throws Exception {
        outputStreamUtil.flush((OutputStream) null);
        assertTrue(true);
    }

    /**
     * 出力ストリームのオブジェクトが存在してない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    @SuppressWarnings("static-access")
    public void testFlush_throwIOException() throws Exception {
        OutputStream out = new IOExceptionOccurOutputStream();
        try {
            outputStreamUtil.flush(out);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    private static class NotifyOutputStream extends OutputStream {
        private String notify_;

        public void write(int arg0) throws IOException {
        }

        public void close() throws IOException {
            super.close();
            notify_ = "closed";
        }

        public void flush() throws IOException {
            super.flush();
            notify_ = "flushed";
        }

        /**
         * @return
         */
        public String getNotify() {
            return notify_;
        }
    }

    private static class IOExceptionOccurOutputStream extends OutputStream {

        public void write(int arg0) throws IOException {
        }

        public void close() throws IOException {
            throw new IOException();
        }

        public void flush() throws IOException {
            throw new IOException();
        }

    }
}
