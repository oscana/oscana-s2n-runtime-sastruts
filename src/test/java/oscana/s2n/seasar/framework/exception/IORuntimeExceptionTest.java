package oscana.s2n.seasar.framework.exception;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/**
 * {@link IORuntimeException}のテスト。
 *
 */
public class IORuntimeExceptionTest {

    /**
     * 引数がExceptionのコンストラクタ場合、オブジェクトを正常に生成できること
     *
     */
    @Test
    public void testException() throws Exception {
        IOException testException = new IOException("testMessage");

        IORuntimeException iORuntimeException = new IORuntimeException(testException);
        assertNotNull(iORuntimeException.getMessage());
    }
}
