package oscana.s2n.seasar.struts.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import mockit.Expectations;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.testCommon.S2NBaseTest;
import oscana.s2n.testCommon.S2NMockHttpRequest;

/**
 * {@link ActionUtil}のテスト。
 * （移植元FM付属テストケース）
 */
public class ActionUtilTest extends S2NBaseTest {

    S2NMockHttpRequest request = new S2NMockHttpRequest();

    /**
     * @throws Exception
     */
    @Test
    public void testFromPathToActionName() throws Exception {
        assertEquals("aaa_bbbAction", ActionUtil
                .fromPathToActionName("/aaa/bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFromActionNameToPath() throws Exception {
        assertEquals("/aaa/bbb", ActionUtil.fromActionNameToPath("aaa_bbbAction"));
        assertEquals("/aaa", ActionUtil.fromActionNameToPath("aaaAction"));
        assertEquals("/index", ActionUtil.fromActionNameToPath("indexAction"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCalcActionPath() throws Exception {
        new Expectations() {{
            httpServletRequest.getPathInfo();
            result = "/aaa/index.jsp";
            minTimes = 0;

        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertEquals("/aaa/", ActionUtil.calcActionPath());
            return null;
        }));

    }

    /**
     * @throws Exception
     */
    @Test
    public void testCalcActionPathWithSlash() throws Exception {
        new Expectations() {{
            httpServletRequest.getPathInfo();
            result = "/aaa/";
            minTimes = 0;

        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {

            assertEquals("/aaa/", ActionUtil.calcActionPath());
            return null;
        }));

    }

    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}