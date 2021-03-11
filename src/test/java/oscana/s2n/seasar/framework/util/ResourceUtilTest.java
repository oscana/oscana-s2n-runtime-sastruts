package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

/**
 * {@link ResourceUtil}のテスト。
 *
 */
public class ResourceUtilTest {

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetResourcePath() throws Exception {
        assertEquals("1", "aaa/bbb.xml", ResourceUtil.getResourcePath(
                "aaa/bbb.xml", "xml"));
        assertEquals("2", "aaa/bbb.xml", ResourceUtil.getResourcePath(
                "aaa.bbb", "xml"));
        assertEquals("3", "aaa.bbb", ResourceUtil.getResourcePath(
                "aaa.bbb", null));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetResource() throws Exception {
        assertNotNull(ResourceUtil.getResource("java/lang/String.class", "class"));
    }

    /**
     * urlが見つけない場合、例外を出力すること
     */
    @Test
    public void testGetResource_throwException() throws Exception {
        try {
            ResourceUtil.getResource("hoge", "xml");
        } catch (RuntimeException e) {
            assertEquals("hoge.xml", e.getMessage());
        }
    }

    /**
     * リソースを取得できない場合、nullを返すこと
     */
    @Test
    public void testGetResourceNoException() throws Exception {
        assertNotNull(ResourceUtil.getResourceNoException(
                "java/lang/String.class", "class"));
        assertNull(ResourceUtil.getResourceNoException(
                "java/lang/String2.class", "class"));
        assertNull(ResourceUtil.getResourceNoException(
                null, "class"));
        assertNull(ResourceUtil.getResourceNoException(
                "java/lang/String.class", "class", null));
    }

    /**
     * リソースをストリームとして取得すること
     */
    @Test
    public void testGetResourceAsStream() throws Exception {
        InputStream inputStream = ResourceUtil.getResourceAsStream("java/lang/String.class");
        assertEquals("class sun.net.www.protocol.jar.JarURLConnection$JarURLInputStream",
                inputStream.getClass().toString());
    }

    /**
     * nullを指定した場合に、 RuntimeExceptionをthrowすること
     */
    @Test(expected=RuntimeException.class)
    public void testGetResourceAsStreamWithNull() throws Exception {
        ResourceUtil.getResourceAsStream(null);
    }

    /**
     * 不正なURLを指定した場合に、 RuntimeExceptionをthrowすること
     */
    @Test(expected=RuntimeException.class)
    public void testGetResourceAsStreamWithNgUrl() throws Exception {
        ResourceUtil.getResourceAsStream("NG");
    }
}