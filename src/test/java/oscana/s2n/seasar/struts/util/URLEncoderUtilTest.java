package oscana.s2n.seasar.struts.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import mockit.Expectations;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.seasar.framework.exception.IORuntimeException;
import oscana.s2n.testCommon.S2NBaseTest;

/**
 * {@link URLEncoderUtil}のテスト。
 *
 */
public class URLEncoderUtilTest extends S2NBaseTest {

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testNew() throws Exception {

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertNotNull(new URLEncoderUtil());
            return null;
        }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testEncode() throws Exception {

        new Expectations() {{
            httpServletRequest.getCharacterEncoding();
            result = "UTF-8";

        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertEquals("javascript%3Aalert%28%27hoge%27%29", URLEncoderUtil.encode("javascript:alert('hoge')"));
            return null;
        }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test(expected = IORuntimeException.class)
    public void testEncodeException() throws Exception {

        new Expectations() {{
            httpServletRequest.getCharacterEncoding();
            result = "EUC";

        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertEquals("javascript%3Aalert%28%27hoge%27%29", URLEncoderUtil.encode("javascript:alert('hoge')"));
            return null;
        }));
    }

    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}