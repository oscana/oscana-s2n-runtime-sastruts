package oscana.s2n.seasar.struts.util;

import static org.junit.Assert.*;

import org.junit.Test;

import oscana.s2n.seasar.framework.util.StringUtil;

/**
 * {@link RoutingUtil}のテスト。</br>
 * （移植元FM付属テストケース）
 *
 */
public class RoutingUtilTest {

    RoutingUtil routingUtil = new RoutingUtil();

    /**
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetActionPath() throws Exception {
        String[] names = StringUtil.split("/aaa", "/");
        assertEquals("/aaa", routingUtil.getActionPath(names, 0));
    }

    /**
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetActionPath_method() throws Exception {
        String[] names = StringUtil.split("/aaa/hoge", "/");
        assertEquals("/aaa", routingUtil.getActionPath(names, 0));
    }

    /**
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetParamPath() throws Exception {
        String[] names = StringUtil.split("/aaa", "/");
        assertEquals("", routingUtil.getParamPath(names, 1));
    }

    /**
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetParamPath_method() throws Exception {
        String[] names = StringUtil.split("/aaa/hoge", "/");
        assertEquals("hoge", routingUtil.getParamPath(names, 1));
    }

    /**
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetParamPath_index() throws Exception {
        String[] names = StringUtil.split("/higayasuo/edit", "/");
        assertEquals("higayasuo/edit", routingUtil.getParamPath(names, 0));
    }
}