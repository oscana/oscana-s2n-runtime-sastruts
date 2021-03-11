package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link ArrayUtil}のテスト
 *
 */
public class ArrayUtilTest {

    ArrayUtil arrayUtil = new ArrayUtil();

    /**
     * 正常系
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testAdd() throws Exception {
        String[] array = new String[] { "111" };
        String[] newArray = (String[]) ArrayUtil.add(array, "222");
        assertEquals("1", 2, newArray.length);
        assertEquals("2", "111", newArray[0]);
        assertEquals("3", "222", newArray[1]);
    }

    /**
     * 配列がnullの場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testAddRuntimeException() throws Exception {
        try {
            String[] array = null;
            arrayUtil.add(array, "222");
        } catch (RuntimeException e) {
            assertEquals("array", e.getMessage());
        }
    }

    /**
     * 配列が空の場合、Trueを戻ること
     * 配列が空ではない場合、Falseを戻ること
     */
    @SuppressWarnings("static-access")
    @Test
    public void testIsEmpty() {
        String[] array1 = null;
        assertTrue(arrayUtil.isEmpty(array1));
        String[] array2 = new String[] {};
        assertTrue(arrayUtil.isEmpty(array2));
        String[] array3 = new String[] { "111" };
        assertFalse(arrayUtil.isEmpty(array3));
    }
}