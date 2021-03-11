package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import oscana.s2n.seasar.framework.exception.IORuntimeException;


/**
 * {@link InputStreamUtil}のテスト。
 *
 */
public class InputStreamUtilTest {

    InputStreamUtil inputStreamUtil = new InputStreamUtil();

    /**
     * {@link InputStream}からbyteの配列を取得できること
     */
    @Test
    @SuppressWarnings("static-access")
    public void testGetBytes() {
        InputStream is = ResourceUtil.getResourceAsStream(StringUtil.replace(
                getClass().getName(), ".", "/")
                + ".class");
        assertNotNull(inputStreamUtil.getBytes(is));
    }

    /**
     * 異常系テスト
     */
    @Test
    @SuppressWarnings("static-access")
    public void testGetBytes_throwException() {
        InputStream input = new IOExceptionOccurInputStream();

        try {
            inputStreamUtil.getBytes(input);
        } catch (Throwable e) {
            assertTrue(e instanceof IORuntimeException);
        }
    }

    /**
     * {@link InputStream}を閉じられること。
     */
    @Test
    @SuppressWarnings("static-access")
    public void testClose() throws Exception {
        NotifyInputStream input = new NotifyInputStream();
        inputStreamUtil.close(input);
        assertEquals("closed", input.getNotify());
    }

    /**
     * {@link InputStream}がnullの場合、何も戻さないこと
     */
    @Test
    @SuppressWarnings("static-access")
    public void testCloseNull() throws Exception {
        inputStreamUtil.close((InputStream) null);
        assertTrue(true);
    }

    /**
     * 指定対象が存在してない場合、IORuntimeExceptionの例外を戻すこと
     */
    @Test
    @SuppressWarnings("static-access")
    public void testClose_throwIOException() throws Exception {
        InputStream input = new IOExceptionOccurInputStream();
        try {
            inputStreamUtil.close(input);
        } catch (Throwable e) {
            assertTrue(e instanceof IORuntimeException);
        }
    }

    /**
     * {@link InputStream}を閉じられること。
     */
    @Test
    @SuppressWarnings("static-access")
    public void testCloseSilently() throws Exception {
        NotifyInputStream input = new NotifyInputStream();
        inputStreamUtil.closeSilently(input);
        assertEquals("closed", input.getNotify());
    }

    /**
     * {@link InputStream}がnullの場合、何も戻さないこと
     */
    @Test
    @SuppressWarnings("static-access")
    public void testCloseSilentlyNull() throws Exception {
        inputStreamUtil.closeSilently((InputStream) null);
        assertTrue(true);
    }

    /**
     * 指定対象が存在してない場合、IORuntimeExceptionの例外を戻すこと
     */
    @Test
    @SuppressWarnings("static-access")
    public void testCloseSilently_throwIOException() throws Exception {
        InputStream input = new IOExceptionOccurInputStream();
        try {
            inputStreamUtil.closeSilently(input);
        } catch (Throwable e) {
            assertTrue(e instanceof IORuntimeException);
        }
    }

    /**
     * 入力ストリームを正常にコピーできること
     * @throws IOException
     */
    @SuppressWarnings("static-access")
    @Test
    public void testCopy() throws IOException {
        NotifyOutputStream out = new NotifyOutputStream();

        InputStream input = ResourceUtil.getResourceAsStream(StringUtil.replace(
                getClass().getName(), ".", "/")
                + ".class");
        inputStreamUtil.copy(input, out);
        out.close();

        assertEquals("closed", out.getNotify());
    }

    /**
     * 存在しない入力ストリームをコピーする場合、IORuntimeExceptionの例外を戻すこと
     */
    @Test
    @SuppressWarnings("static-access")
    public void testCopy_throwException() {
        InputStream input = new IOExceptionOccurInputStream();
        OutputStream out = null;

        try {
            inputStreamUtil.copy(input, out);
        } catch (Throwable e) {
            assertTrue(e instanceof IORuntimeException);
        }
    }

    /**
     * 入力ストリームが存在する場合、正常に呼出できること
     */
    @Test
    @SuppressWarnings("static-access")
    public void testAvailable() throws Exception {
        NotifyInputStream input = new NotifyInputStream();
        input.close();
        assertEquals(0, inputStreamUtil.available(input));
    }

    /**
     * 入力ストリームが存在しない場合、IORuntimeExceptionの例外を戻すこと
     */
    @Test
    @SuppressWarnings("static-access")
    public void testAvailable_throwException() throws Exception {
        InputStream input = new IOExceptionOccurInputStream();
        try {
            inputStreamUtil.available(input);
        } catch (Throwable e) {
            assertTrue(e instanceof IORuntimeException);
        }
    }

    /**
     * {@link InputStream}をリセットできること
     */
    @Test
    @SuppressWarnings("static-access")
    public void testReset_throwException() throws Exception {
        NotifyInputStream input = new NotifyInputStream();
        try {
            inputStreamUtil.reset(input);
        } catch (Throwable e) {
            assertTrue(e instanceof IORuntimeException);
            assertEquals("java.io.IOException: mark/reset not supported", e.getMessage());

        }
    }

    private static class NotifyInputStream extends InputStream {
        private String notify_;

        public void close() throws IOException {
            super.close();
            notify_ = "closed";
        }

        /**
         * @return
         */
        public String getNotify() {
            return notify_;
        }

        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    private static class IOExceptionOccurInputStream extends InputStream {

        public void close() throws IOException {
            throw new IOException();
        }

        public int available() throws IOException {
            throw new IOException();
        }

        @Override
        public int read() throws IOException {
            throw new IOException();
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

}
