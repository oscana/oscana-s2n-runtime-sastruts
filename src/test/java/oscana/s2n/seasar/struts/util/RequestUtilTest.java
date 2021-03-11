package oscana.s2n.seasar.struts.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import mockit.Expectations;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.testCommon.S2NBaseTest;
import oscana.s2n.testCommon.S2NMockHttpServletRequest;

/**
 * {@link RequestUtil}のテスト。
 *
 */
public class RequestUtilTest extends S2NBaseTest {

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetRequest() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertNotNull(RequestUtil.getRequest());
            return null;
        }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetPath_viewPath() throws Exception {
        new Expectations() {{
            httpServletRequest.getPathInfo();
            result = "/WEB-INF/jsp/hoge.jsp";
            minTimes = 0;

            servletContext.getInitParameter(anyString);
            result = "/WEB-INF/jsp";

        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertEquals("/hoge.jsp", RequestUtil.getPath());

            return null;
        }));
    }

    /**
     * Viewプレフィックスがnullの場合、パスを戻すこと
     * @throws Exception
     */
    @Test
    public void testGetPath_viewPrefixIsNull() throws Exception {
        new Expectations() {{
            httpServletRequest.getPathInfo();
            result = "/WEB-INF/jsp/hoge.jsp";
            minTimes = 0;

            servletContext.getInitParameter(anyString);
            result = null;

        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            servletContext.setInitParameter("sastruts.VIEW_PREFIX", null);

            assertNotNull(RequestUtil.getPath());

            return null;
        }));
    }

    /**
     * Viewパスがnullの場合、nullを戻すこと
     * @throws Exception
     */
    @Test
    public void testGetPath_viewPathIsNull() throws Exception {
        assertNull(RequestUtil.getPath(new S2NMockHttpServletRequest()));
    }

    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}