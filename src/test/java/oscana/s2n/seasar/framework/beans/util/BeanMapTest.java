package oscana.s2n.seasar.framework.beans.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link BeanMap}のテスト。
 *
 */
public class BeanMapTest {

    /**
     * BeanMapを値取得のテスト
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception {
        BeanMap map = new BeanMap();
        map.put("aaa", 1);
        map.put("bbb", 2);
        assertEquals(1, map.get("aaa"));
        try {
            map.get("xxx");
            fail();
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("xxx is not found in [aaa, bbb]", e.getMessage());
        }
    }

}
