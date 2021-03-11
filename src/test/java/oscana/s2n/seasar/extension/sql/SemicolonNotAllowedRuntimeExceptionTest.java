package oscana.s2n.seasar.extension.sql;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link SemicolonNotAllowedRuntimeException}のテスト。
 *
 */
public class SemicolonNotAllowedRuntimeExceptionTest {

    /**
     * SemicolonNotAllowedRuntimeExceptionのオブジェクトを正常に生成できること
     *
     */
    @Test
    public void testException() throws Exception {
        SemicolonNotAllowedRuntimeException testException = new SemicolonNotAllowedRuntimeException();
        assertNotNull(testException);
    }
}
